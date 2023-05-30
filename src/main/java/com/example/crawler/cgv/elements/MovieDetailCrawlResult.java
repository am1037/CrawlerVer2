package com.example.crawler.cgv.elements;

import com.example.crawler.MyCrawlingResult;
import com.example.database.mySQL.mybatis.ScreenVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MovieDetailCrawlResult extends MyCrawlingResult {
    String url;
    String title;
    String titleOther;
    List<String> directors;
    List<String> actors;
    List<String> genres;
    String age;
    String runtime;
    List<String> nations;
    String releaseDate;
    String story;
}
