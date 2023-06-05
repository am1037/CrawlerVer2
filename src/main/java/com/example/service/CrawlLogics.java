package com.example.service;

import com.example.crawler.cgv.CrawlerCGV;
import com.example.crawler.cgv.elements.CgvCrawlResult;
import com.example.crawler.cgv.elements.CgvMovieDetailCrawlResult;
import com.example.crawler.lotte.CrawlerLotte;
import com.example.crawler.lotte.elements.LotteCrawlResult;
import com.example.crawler.lotte.elements.LotteMovieDetailCrawlResult;
import com.example.database.external.kobis.KobisAPI;
import com.example.database.external.kobis.mapping.MappingMapper;
import com.example.database.external.kobis.mapping.MappingObject;
import com.example.database.mySQL.mybatis.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//@Service
@RestController
public class CrawlLogics {

    @Autowired
    CrawlerCGV cgv;
    @Autowired
    CrawlerLotte lotte;
    @Autowired
    ScreenMapper screenMapper;
    @Autowired
    TheaterMapper theaterMapper;
    @Autowired
    MovieMappingMapper movieMappingMapper;
    @Autowired
    KobisAPI kobisAPI;
    @Autowired
    MappingMapper mappingMapper;

    @RequestMapping(value = "/crawloo", produces = "text/html;charset=UTF-8")
    public String crawlOneDayOneTheater(@RequestParam String date,
                                        @RequestParam String company,
                                        @RequestParam String theaterId) {
        if (company.equals("CGV")) {
            CgvCrawlResult cgvCrawlResult = cgv.crawl(theaterId, date);
            //insert(cgvCrawlResult);
            for(ScreenVO vo : cgvCrawlResult.toScreenVOs()){
                try {
                    screenMapper.insert(vo);
                } catch (DuplicateKeyException e) {
                    System.out.println("DuplicateKeyException");
                }
            }
            return cgvCrawlResult.toJsonString();

        } else if (company.equals("LOTTE")) {
            LotteCrawlResult lotteCrawlResult = lotte.crawl(theaterId, date);
            //insert(lotteCrawlResult);
            for (ScreenVO vo : lotteCrawlResult.toScreenVOs()) {
                try {
                    screenMapper.insert(vo);
                } catch (DuplicateKeyException e) {
                    System.out.println("DuplicateKeyException");
                }
            }
            return lotteCrawlResult.toJsonString();
        } else {
            return "{ message : company is invalid }";
        }
        //return "{ message : success }";
    }

    @RequestMapping(value = "/crawlo", produces = "text/html;charset=UTF-8")
    public String crawlOneDay(@RequestParam String date,
                              @RequestParam String company) {
        Date today = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(today).append("<br><br>");
        List<TheaterVO> voList = theaterMapper.selectListByCompanyName(company);
        for(TheaterVO vo : voList) {
            sb.append(crawlOneDayOneTheater(date, company, vo.getTheater_id())).append("<br><br>");
        }
        today = new Date();
        sb.append("<br><br>").append(today);
        return sb.toString();
    }

    //Screen Table을 검사해서 Movie Mapping Table에 없는 URL만 집어넣습니다.
    @RequestMapping(value = "mmInsert", produces = "text/html;charset=UTF-8")
    public String movieMappingInsert(){
        List<ScreenVO> screenVOList = screenMapper.selectByMovieCdNull();
        StringBuilder sb = new StringBuilder();
        Set<String> urlSet = new HashSet<>();
        screenVOList.forEach(x -> {
            urlSet.add(x.getDetail_url());
        });
        urlSet.forEach(x -> {
            MovieMappingVO mm = new MovieMappingVO();
            mm.setDetail_url(x);
            try {
                movieMappingMapper.insertOne(mm);
                sb.append(x).append("<br>");
            } catch (DuplicateKeyException e) {
                System.out.println("중복");
            }
        });
        return sb.toString();
    }

    //Movie Mapping Table을 검사해서 movieCd가 비어있는 테이블들을 찾아내고
    //비교 메소드를 통해 채워 넣습니다.
    @RequestMapping(value = "/mmUpdate", produces = "text/html;charset=UTF-8")
    public String movieMappingUpdate(){
        Date today = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(today).append("<br><br>");

        List<String> list = movieMappingMapper.selectAllMovieCdNULL();
        System.out.println(list.size());
        for(String str : list){
            try {
                MovieMappingVO vo = new MovieMappingVO();;
                vo.setDetail_url(str);
                String[] result = compare(str);
                vo.setKobis_movieCd(result[0]);
                vo.setMy_title(result[1]);
                if(str.contains("CGV"))
                    vo.setCompany("CGV");
                else if(str.contains("LOTTE"))
                    vo.setCompany("LOTTE");
                movieMappingMapper.updateMovieCdByUrl(vo);
                sb.append(vo).append("<br>");
            }catch (Exception e){
                e.printStackTrace();
                MovieMappingVO vo = new MovieMappingVO();
                vo.setDetail_url(str);
                vo.setKobis_movieCd("!NOT FOUND!");
            }
        }
        today = new Date();
        sb.append("<br><br>").append(today);
        return sb.toString();
    }

    public String[] compare(String str) throws Exception{
        String[] result = new String[2];
        if(str.contains("cgv")){
            CgvMovieDetailCrawlResult cgvMovieDetailCrawlResult = cgv.crawlMovie(str);
            MappingObject mo = new MappingObject(cgvMovieDetailCrawlResult);
            result[0] = mappingMapper.whichOne(mo, kobisAPI.byCrawlResult(cgvMovieDetailCrawlResult));
            result[1] = cgvMovieDetailCrawlResult.getTitle();
            return result;
        }else if(str.contains("lotte")){
            LotteMovieDetailCrawlResult lotteMovieDetailCrawlResult = lotte.crawlMovie(str);
            MappingObject mo = new MappingObject(lotteMovieDetailCrawlResult);
            result[0] = mappingMapper.whichOne(mo, kobisAPI.byCrawlResult(lotteMovieDetailCrawlResult));
            result[1] = lotteMovieDetailCrawlResult.getMovie().getTitle();
            return result;
        }else {
            return null; //null이면 어떻게 처리할지 생각해보자
        }
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
