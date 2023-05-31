package com.example.database.external.kmdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class KmdbAPI {
    String key = "&ServiceKey="+System.getenv("key4kmdb");
    String str = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&listCount=500";
    public KmdbMovieSimpleInfoResponseVO getMovieInfoByTitle(String title){
        try {
            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            HttpURLConnection connection = (HttpURLConnection) new URL(str+key+"&title="+encoded).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper om = new ObjectMapper();
            return om.readValue(connection.getInputStream(), KmdbMovieSimpleInfoResponseVO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String key = "&ServiceKey="+System.getenv("key4kmdb");
        String str = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&listCount=20";
        KmdbAPI kmdbAPI = new KmdbAPI();
        String title = "캐리 러브 콘서트";

        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            HttpURLConnection connection = (HttpURLConnection) new URL(str+key+"&title="+encodedTitle).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper om = new ObjectMapper();
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            System.out.println(sb.toString());
//            KmdbMovieSimpleInfoResponseVO vo = kmdbAPI.getMovieInfoByTitle(title);
//            System.out.println(vo.getData().get(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
