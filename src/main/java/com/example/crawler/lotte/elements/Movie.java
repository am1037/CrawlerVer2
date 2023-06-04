package com.example.crawler.lotte.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Movie {
    @JsonProperty("MovieNameKR")
    String title;

    @JsonProperty("MovieNameUS")
    String titleOther;

    @JsonProperty("PlayTime")
    Integer runtime;

    @JsonProperty("MakingNationNameKR")
    String nations;

    @JsonProperty("ReleaseDate")
    String releaseDate;
    public void setReleaseDate(String releaseDate) {
        // 2023-05-24 오전 12:00:00 to yyyyMMdd
        this.releaseDate = releaseDate.substring(0, 10).replace("-", "");
    }

    @JsonProperty("SynopsisKR")
    String story;

    @JsonProperty("ViewGradeCode")
    String grade;
    @JsonSetter("ViewGradeCode")
    public void setGrade(String age) {
        System.out.println(age);
        //left only numbers
        this.grade = age.replaceAll("[^0-9]", "");
        if(this.grade.equals("")){
            if(age.contains("청소년")) this.grade = "18";
            else this.grade = "0";
        }
    }
}
