package com.example.crawler.lotte;

import com.example.crawler.lotte.elements.LotteCrawlResult;
import com.example.crawler.lotte.elements.LotteMovieDetailCrawlResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
public class CrawlerLotte {

    public static void main(String[] args) {
        CrawlerLotte crawlerLotte = new CrawlerLotte();
        //LotteCrawlResult result = crawlerLotte.crawl("1004", "20230604");
        LotteMovieDetailCrawlResult result = crawlerLotte.crawlMovie("https://www.lottecinema.co.kr/NLCHS/Movie/MovieDetailView?movie=19885");
        //System.out.println(result);
        System.out.println(result.getMovie().getTitle());
        System.out.println(result.getMovie().getGrade());
    }

    public LotteCrawlResult crawl(String theater, String date) {
        String url = "https://www.lottecinema.co.kr/LCWS/Ticketing/TicketingData.aspx";
        date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            String str = "paramList={\"MethodName\":\"GetPlaySequence\",\"channelType\":\"MW\",\"osType\":\"W\",\"osVersion\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36\",\"playDate\":\""+ date +"\",\"cinemaID\":\"1|0999|"+ theater +"\",\"representationMovieCode\":\"\"}";
            os.write(str.getBytes());
            os.flush();
            os.close();
            InputStream is = con.getInputStream();
            int read = 0;
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((read = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, read));
            }
            ObjectMapper om = new ObjectMapper();
            LotteCrawlResult result = om.readValue(sb.toString(), LotteCrawlResult.class);
            result.setTheater_code(theater);
            result.setFile_targetDate(date);
            result.setFile_createdDate(new Date());
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //https://www.lottecinema.co.kr/NLCHS/Movie/MovieDetailView?movie=19874
    public LotteMovieDetailCrawlResult crawlMovie(String str1) {
        String url = "https://www.lottecinema.co.kr/LCWS/Movie/MovieData.aspx";
        String str2 = str1.substring(str1.length()-5);

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            String str = "paramList={\"MethodName\":\"GetMovieDetailTOBE\",\"channelType\":\"HO\",\"osType\":\"Chrome\",\"osVersion\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36\",\"multiLanguageID\":\"KR\",\"representationMovieCode\":\"" + str2 + "\",\"memberOnNo\":\"\"}";
            os.write(str.getBytes());
            os.flush();
            os.close();
            InputStream is = con.getInputStream();
            int read = 0;
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((read = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, read));
            }

            ObjectMapper om = new ObjectMapper();
            LotteMovieDetailCrawlResult result = om.readValue(sb.toString(), LotteMovieDetailCrawlResult.class);
            result.setUrl(str1);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
