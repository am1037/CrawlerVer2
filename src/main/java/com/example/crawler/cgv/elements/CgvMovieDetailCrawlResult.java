package com.example.crawler.cgv.elements;

import com.example.crawler.MyCrawlingResult;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CgvMovieDetailCrawlResult extends MyCrawlingResult {
    String url;
    String title;
    String titleOther;
    List<String> directors;
    List<String> actors;
    List<String> genres;

    String age;
    @JsonSetter("age")
    public void setAge(String age) {
        //left only numbers
        this.age = age.replaceAll("[^0-9]", "");
        if(this.age.equals("")){
            if(age.contains("청소년")) this.age = "18";
            else this.age = "0";
        }
    }

    String runtime;
    List<String> nations;
    String releaseDate;
    String story;
}
