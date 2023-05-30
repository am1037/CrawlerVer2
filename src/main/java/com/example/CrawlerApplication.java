package com.example;

import com.example.database.mySQL.mybatis.MovieMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

//    @Autowired
//    MovieMappingMapper movieMappingMapper;
//
//    @Bean
//    CommandLineRunner runner() {
//        return args -> {
//            System.out.println(movieMappingMapper.getAllMappings());
//        };
//    }

}
