package com.example.crawler.lotte.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
}
