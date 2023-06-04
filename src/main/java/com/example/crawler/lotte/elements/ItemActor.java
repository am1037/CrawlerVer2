package com.example.crawler.lotte.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemActor {
    @JsonProperty("StaffName")
    String StaffName;

    @JsonProperty("Role")
    String Role;
}

/*
{
        "CinemaNameKR": "가산디지털",
        "MovieNameKR": "범죄도시3",
        "MovieNameUS": "THE ROUNDUP : NO WAY OUT",
        "CinemaID": 1013,
        "MovieCode": "19803",
        "PlayDt": "2023-06-03",
        "ScreenID": 101301,
        "PlaySequence": 6,
        "StartTime": "21:25",
        "EndTime": "23:20",
        "ScreenNameKR": "1관",
        "ScreenNameUS": "CINEMA 1"
      }

        "RepresentationMovieCode": "19874",
        "StaffName": "롭 마샬",
        "StaffImage": "http://caching.lottecinema.co.kr//Media/MovieFile/PersonImg/22000/21333_107_1.jpg",
        "Role": "감독"
 */