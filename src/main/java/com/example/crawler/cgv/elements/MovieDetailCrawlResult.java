package com.example.crawler.cgv.elements;

import com.example.crawler.MyCrawlingResult;
import lombok.Data;

import java.util.List;

@Data
public class MovieDetailCrawlResult extends MyCrawlingResult {
    String URL;
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
