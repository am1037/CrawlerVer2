package com.example.service;

import com.example.crawler.cgv.CrawlerCGV;
import com.example.crawler.cgv.elements.ColTime;
import com.example.crawler.cgv.elements.CgvCrawlResult;
import com.example.crawler.cgv.elements.CgvMovieDetailCrawlResult;
import com.example.database.external.kmdb.KmdbAPI;
import com.example.database.external.kmdb.KmdbMovieSimpleInfoResponseVO;
import com.example.database.mongoDB.DoorToMongoDB;
import com.example.database.mySQL.mybatis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//@Service
@RestController
public class CrawlLogics {

    @Autowired
    CrawlerCGV cgv;
    @Autowired
    MovieMappingMapper mmm;
    @Autowired
    TheaterMapper tm;
    @Autowired
    KmdbAPI kmdbAPI;
    @Autowired
    ScreenMapper sm;
    @Autowired
    DoorToMongoDB doorToMongoDB;


    // /crawl?region=서울&fromDate=20230530&untilDate=20230531
    @RequestMapping("/crawl")
    public String crawlMany(@RequestParam(required = false) String theater_Id,
                            @RequestParam(required = false) String region,
                            @RequestParam(required = false) String fromDate,
                            @RequestParam(required = false) String untilDate) {
        if(theater_Id == null && region == null){
            return "theater_Id or region is required";
        }
        if(theater_Id != null && region != null){
            return "theater_Id and region cannot be used at the same time";
        }
        if(fromDate == null || untilDate == null){
            return "fromDate and untilDate is required";
        }

        List<Theater> targetTheaterList;
        if(region != null){
            targetTheaterList = sm.selectByRegion(region);
        }else{
            targetTheaterList = new ArrayList<>();
            targetTheaterList.add(tm.getTheaterById("CGV", theater_Id));
        }

        List<String> dateList = betweenTwoDate(fromDate, untilDate);

        for(Theater t : targetTheaterList){
            for(String date : dateList){
                doorToMongoDB.insertOne(crawl(t.getTheater_id(), date));
            }
        }

        return "Crawling Done";
    }

    @RequestMapping("/crawlEverywhere")
    public String crawlOneday(@RequestParam(required = false) String date) {

        List<Theater> targetTheaterList = tm.selectAllByCompanyName("CGV");

        for (Theater t : targetTheaterList) {
            crawl(t.getTheater_id(), date);
        }
        //crawl(targetTheaterList, date);
        fillDOCID();

        return "Crawling Done";
    }

    //@RequestMapping("/c1") c2에 포함되어 있으므로 일단 비활성화
    public String fillMappingTable(){
        /*
        일단 mapping table(mysql)에서 movie id가 미지정, 즉 null인 row들을 찾는다
        이 row들의 url을 기록한다
        1.url에서 크롤링한 정보들도 가지고 있는 테이블은 mongoDB에 있다. 이를 확인한다.
        2.이 안에 있는 정보들, 이를테면 제목, 감독명, 영문명 등을 이용하여 kmdb에 쿼리한다.
        1(mongo)과 2(kmdb)에서 얻은 정보들을 교차하여 조건을 만족하면 id를 지정한다. 이 결과는 mySQL측에 저장된다.
         */
        Map<String, MovieMapping> mmMap  = mmm.getAllMappingsIdIsNull();
        mmMap.forEach((x, y)->{
            CgvMovieDetailCrawlResult infoFromMongoDB = doorToMongoDB.selectOneByUrl(x);
            KmdbMovieSimpleInfoResponseVO infoFromKmdb = kmdbAPI.getMovieInfoByTitle(infoFromMongoDB.getTitle());
            List<KmdbMovieSimpleInfoResponseVO.Collection.Movie> movieList = infoFromKmdb.getMovies(0);
            if(movieList == null){
                mmm.updateDocidAndPosters(x, "!NOT FOUND", "!NOT FOUND", "!NOT FOUND");
            }else if(movieList.size() == 1) {
                mmm.updateDocidAndPosters(x, movieList.get(0).getDOCID(), movieList.get(0).getKmdbUrl(), movieList.get(0).getPosters());
            }else if(movieList.size() > 1){
                movieList = infoFromKmdb.getMovies(0).stream().filter(m -> m.getRuntime().equals(infoFromMongoDB.getRuntime())).collect(Collectors.toList());
                if(movieList.size() == 1) {
                    mmm.updateDocidAndPosters(x, movieList.get(0).getDOCID(), movieList.get(0).getKmdbUrl(), movieList.get(0).getPosters());
                }else if(movieList.size() > 1){
                    movieList = infoFromKmdb.getMovies(0).stream().filter(m -> m.getTitleEng().equals(infoFromMongoDB.getTitleOther())).collect(Collectors.toList());
                }else {
                    mmm.updateDocidAndPosters(x, "!NOT FOUND", "!NOT FOUND", "!NOT FOUND");
                }
            }
        });
        return "Done";
    }

    @RequestMapping("/c2")
    public String fillDOCID(){
        Date now = new Date();
        String returnStr = "c2 : fillDOCID\n";
        returnStr += "Start Time : " + now.toString() + "\n";

        //일단 영화 목록에서 docid가 null인 경우를 가져온다
        List<ScreenVO> vos = sm.selectByDocidNull();
        Set<String> set = new HashSet<>();
        vos.forEach(vo -> {
            set.add(vo.getDetail_url());
        });


        //이 url들을 mapping table에 넣는다
        for(String s : set){
            MovieMapping mm = new MovieMapping();
            mm.setCompany("CGV");
            mm.setDetail_url(s);
            try {
                mmm.insertUrl(mm);
            }catch (Exception e){
                System.out.println("Already Exist");
            }
        }

        fillMappingTable();

        Set<MovieMapping> mmSet = new HashSet<>();
        for (String s : set){
            mmSet.add(mmm.getMappingByUrl(s));
        }

        for(MovieMapping m : mmSet){
            sm.updateDocid(m.getDocid(), m.getDetail_url());
        }

        now = new Date();
        returnStr += "End Time : " + now.toString() + "\n";

        return returnStr;
    }

    @RequestMapping("/c3")
    public String fillTitle(){
        String returnStr = "c3 : fillTitle\n";
        Date now = new Date();
        returnStr += "Start Time : " + now.toString() + "\n";

        Map<String, MovieMapping> mmMap = mmm.getAllMappingsTitleIsNull();
        for(String s : mmMap.keySet()){
            mmm.updateTitleByUrl(s, doorToMongoDB.selectOneByUrl(s).getTitle());
        }

        now = new Date();
        returnStr += "End Time : " + now.toString() + "\n";
        return returnStr;
    }

    public CgvCrawlResult crawl(@RequestParam String theaterId,
                                @RequestParam String date) {
        System.out.println("Crawling Start... " + theaterId + " " + date);
        System.out.println("Crawling Start... " + theaterId + " : " + theaterCode("CGV", theaterId));
        List<ColTime> colTimeList;
        try {
            CgvCrawlResult cr = cgv.crawl(theaterId, date);
            colTimeList = cr.getColTimes();

            colTimeList.forEach(ct -> {
                /*
                일단 mapper db에는 죄다 넣는다
                pk가 알아서 걸러줄 것이다
                 */
                try {
                    MovieMapping mm = new MovieMapping();
                    mm.setCompany("CGV");
                    mm.setDetail_url(ct.getUrl());
                    mmm.insertUrl(mm);
                }catch (DuplicateKeyException e){
                    System.out.println("Duplicate Entry");
                }

                if(!isExistMongo(ct.getUrl())) { //존재하지 않을 경우
                    //cgv에서 크롤해서 채워넣는다
                    System.out.println("Crawling Movie Detail... " + ct.getUrl());
                    CgvMovieDetailCrawlResult result = cgv.crawlMovie(ct.getUrl());
                    doorToMongoDB.insertOne(result);
                }
            });

            /*
            MongoDB의 정보는 추후 kmdb 등과의 대조 후 고유 id를 부여하기 위해 사용하지만,
            일단 크롤링 한 상영관 자체는 우리쪽 db에 집어넣는다
             */
            try {
                for(ScreenVO s : cr.toScreenVOs()) {
                    sm.insert(s);
                }
                return cr;
            } catch (Exception e) {
                e.printStackTrace();
                cr = new CgvCrawlResult();
                cr.setCompany("ERROR");
                return cr;
            }
        }catch (Exception e){
            doorToMongoDB.insertOne(e.getMessage(), theaterId, date);
        }
        return null;
    }

    public boolean isExistMongo(String url) {
        return doorToMongoDB.selectOneByUrl(url) != null;
    }

    public String theaterCode(@RequestParam String theater_company,
                              @RequestParam String theater_id){
        return tm.getTheaterById(theater_company, theater_id).getTheater_name();
    }
    public List<KmdbMovieSimpleInfoResponseVO.Collection.Movie> getMovieInfoByTitle(@RequestParam String title) {
        return kmdbAPI.getMovieInfoByTitle(title).getMovies(0);
    }

    public List<String> betweenTwoDate(String date1, String date2){
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date;
        try {
            date = sdf.parse(date1);
            while (!sdf.format(date).equals(date2)){
                list.add(sdf.format(date));
                date = new Date(date.getTime() + (1000 * 60 * 60 * 24));
            }
            list.add(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}
