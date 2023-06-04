package com.example.database.external.kobis.mapping;

import com.example.crawler.cgv.CrawlerCGV;
import com.example.crawler.cgv.elements.CgvMovieDetailCrawlResult;
import com.example.crawler.lotte.CrawlerLotte;
import com.example.database.external.kobis.KobisAPI;
import com.example.database.external.kobis.elements.KobisMovieInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MappingMapper {

    @Autowired
    KobisAPI kobisAPI;

    public static void main(String[] args) throws Exception {
        CrawlerLotte crawlerLotte = new CrawlerLotte();
        CrawlerCGV crawlerCGV = new CrawlerCGV();
        KobisAPI kobisAPI = new KobisAPI();
        MappingMapper mm = new MappingMapper();
        //System.out.println(mm.whichOne(new MappingObject(crawlerCGV.crawlMovie("http://www.cgv.co.kr/movies/detail-view/?midx=87081")), kobisAPI.byCrawlResult(crawlerCGV.crawlMovie("http://www.cgv.co.kr/movies/detail-view/?midx=87081"))));
        System.out.println(kobisAPI.byMovieCd("20231957"));
        //System.out.println(kobisAPI.byCrawlResult(crawlerCGV.crawlMovie("http://www.cgv.co.kr/movies/detail-view/?midx=86991")));
    }

    public String whichOne(MappingObject mo, List<KobisMovieInfoResponse> responses){
        if(responses.size()==1){
            return responses.get(0).getKobisMovieInfoResult().getMovieInfo().getMovieCd();
        }else if(responses.size()==0){
            return "!NOT FOUND!";
        }
        else {
            for(KobisMovieInfoResponse response : responses){
                if(response.getKobisMovieInfoResult().getMovieInfo().getShowTm().equals(mo.getRuntime())){
                    return response.getKobisMovieInfoResult().getMovieInfo().getMovieCd();
                }
            }
            responses.sort((o1, o2) -> {
                int o1Diff = Math.abs(o1.getKobisMovieInfoResult().getMovieInfo().getShowTm() - mo.getRuntime());
                int o2Diff = Math.abs(o2.getKobisMovieInfoResult().getMovieInfo().getShowTm() - mo.getRuntime());
                return o1Diff - o2Diff;
            });
            if(responses.get(0).getKobisMovieInfoResult().getMovieInfo().getAudits().size() == 1 && !responses.get(0).getKobisMovieInfoResult().getMovieInfo().getAudits().get(0).getWatchGradeNm().contains("학생")){
                if(responses.get(0).getKobisMovieInfoResult().getMovieInfo().getAudits().get(0).getWatchGradeNm().equals(mo.getGrade())) {
                    return responses.get(0).getKobisMovieInfoResult().getMovieInfo().getMovieCd();
                }
            }else {
                return responses.get(0).getKobisMovieInfoResult().getMovieInfo().getMovieCd();
            }
        }
        return "!NOT FOUND!";
    }

}
