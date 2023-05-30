package com.example.database.external.kmdb;

import lombok.Data;
//
@Data
public class KmdbMovieInfoRequest {
    private String serviceKey; // required
    private Integer listCount; // more than 3
    private Integer startCount;
    private String collection;
    private String query;
    private String detail;
    private String sort;
    private String createDts;
    private String createDte;
    private String releaseDts;
    private String releaseDte;
    private String nation;
    private String company;
    private String genre;
    private String ratedYn;
    private String use;
    private String movieId;
    private String movieSeq;
    private String type;
    private String title;
    private String director;
    private String actor;
    private String staff;
    private String keyword;
    private String plot;
}
