package com.example.controller;

import com.example.crawler.cgv.CrawlerCGV;
import com.example.database.mySQL.mybatis.MovieMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @Autowired
    CrawlerCGV cgv;

    @Autowired
    MovieMappingMapper mmm;

    @RequestMapping("/")
    public String hello() {
        System.out.println(mmm.selectAll());
        return "Hello World!";
    }
}
