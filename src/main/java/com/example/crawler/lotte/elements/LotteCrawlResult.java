package com.example.crawler.lotte.elements;

import com.example.crawler.MyCrawlingResult;
import com.example.crawler.cgv.elements.CgvScreen;
import com.example.crawler.cgv.elements.ColTime;
import com.example.database.mySQL.mybatis.ScreenVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteCrawlResult extends MyCrawlingResult {

    String company = "LOTTE";
    String theater_code;
    String file_targetDate;
    String file_createdDate;

    public void setFile_targetDate(String file_targetDate) {
        this.file_targetDate = file_targetDate.replace("-", "");
    }

    public void setFile_targetDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        this.file_targetDate = format.format(date);
    }

    public void setFile_createdDate(String file_createdDate) {
        this.file_createdDate = file_targetDate.replace("-", "");
    }

    public void setFile_createdDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        this.file_createdDate = format.format(date);
    }

    @JsonProperty("PlaySeqs")
    PlaySeqs playSeqs;

    public List<ScreenVO> toScreenVOs(Map<String, String> map) {
        //map : movieCode NNNNN, runtime NNN
        List<ScreenVO> screenVOs = new ArrayList<>();
        for (Item i : playSeqs.getItems()) {
                ScreenVO screenVO = new ScreenVO();
                screenVO.setTitle(i.getCinemaNameKR());
                screenVO.setCompany(company);
                screenVO.setScreen_name(i.getScreenName());
                screenVO.setRuntime(Integer.parseInt(map.get(i.getMovieCode())));
                screenVO.setDetail_url("https://www.lottecinema.co.kr/NLCHS/Movie/MovieDetailView?movie="+i.getMovieCode());
                screenVO.setTime(i.getStartTime());
                screenVO.setTime_end(i.getEndTime());
                screenVO.setDate(file_targetDate);
                screenVO.setTheater_id(theater_code);
                screenVOs.add(screenVO);
        }
        return screenVOs;
    }
}
