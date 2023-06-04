package com.example.database.external.kobis.mapping;

import com.example.crawler.cgv.elements.CgvMovieDetailCrawlResult;
import com.example.crawler.lotte.elements.LotteMovieDetailCrawlResult;
import com.example.database.external.kobis.elements.KobisMovieInfoResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MappingObject {

    String title;
    Integer runtime;
    String grade;

    public MappingObject() {
        // default constructor
    }

    public MappingObject(CgvMovieDetailCrawlResult cgvMovieDetailCrawlResult) {
        this.title = cgvMovieDetailCrawlResult.getTitle();
        this.runtime = Integer.parseInt(cgvMovieDetailCrawlResult.getRuntime());
        this.grade = cgvMovieDetailCrawlResult.getAge();
    }

    public MappingObject(LotteMovieDetailCrawlResult lotteMovieDetailCrawlResult) {
        this.title = lotteMovieDetailCrawlResult.getMovie().getTitle();
        this.runtime = lotteMovieDetailCrawlResult.getMovie().getRuntime();
        this.grade = lotteMovieDetailCrawlResult.getMovie().getGrade();
    }

    public MappingObject(KobisMovieInfoResponse response){
        this.title = response.getKobisMovieInfoResult().getMovieInfo().getMovieNm();
        this.runtime = response.getKobisMovieInfoResult().getMovieInfo().getShowTm();
        this.grade = response.getKobisMovieInfoResult().getMovieInfo().getAudits().get(0).getWatchGradeNm();
    }
}
