package com.example.crawler.cgv.elements;

import com.example.crawler.MyCrawlingResult;
import com.example.database.mySQL.mybatis.ScreenVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CgvCrawlResult extends MyCrawlingResult {

    String company = "CGV";
    String theater_code;
    String file_targetDate;
    String file_createdDate;
    List<ColTime> colTimes; //sectShowtimes

    public List<ScreenVO> toScreenVOs() {
        List<ScreenVO> screenVOs = new ArrayList<>();
        for (ColTime ct : colTimes) {
            for (CgvScreen s : ct.getScreens()) {
                ScreenVO screenVO = new ScreenVO();
                screenVO.setTitle(ct.getMovie_title());
                screenVO.setCompany(company);
                screenVO.setScreen_name(s.getScreen_name());
                screenVO.setRuntime(ct.getRuntime());
                screenVO.setDetail_url(ct.getUrl());
                screenVO.setTime(s.getScreen_startTime());
                screenVO.setTime_end(s.getScreen_endTime());
                screenVO.setDate(file_targetDate);
                screenVO.setTheater_id(theater_code);
                screenVOs.add(screenVO);
            }
        }
        return screenVOs;
    }
}
