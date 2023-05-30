package com.example.crawler;

import com.example.crawler.cgv.elements.CrawlResult;
import com.example.crawler.cgv.elements.MovieDetailCrawlResult;

public interface CrawlerInterface {

    public CrawlResult crawl(String theater, String date);
    public MovieDetailCrawlResult crawlMovie(String url);

}
