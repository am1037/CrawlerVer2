package com.example.crawler.cgv.elements;

import lombok.Data;

import java.util.List;

@Data
public class ColTime {
    String movie_title;
    List<String> movie_genres;
    String runtime;
    String release_date;
    String url;

    List<Screen> screens;
}
