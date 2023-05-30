package com.example.crawler.cgv.elements;

import com.example.crawler.MyCrawlingResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class CrawlResult extends MyCrawlingResult {

    String company;
    String theater_code;
    String file_targetDate;
    String file_createdDate;
    List<ColTime> colTimes; //sectShowtimes

}
