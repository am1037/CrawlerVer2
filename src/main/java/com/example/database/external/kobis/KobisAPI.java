package com.example.database.external.kobis;

import com.example.database.external.kobis.elements.KobisMovieInfoResponse;
import com.example.database.external.kobis.elements.KobisMovieListResponse;
import com.example.database.external.kobis.elements.KobisMovieListResult;
import com.example.database.external.kobis.elements.KobisMovieVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KobisAPI {
    String kobis_KEY = "102955b1e67ec69156fad182a886776b";
    KobisOpenAPIRestService kobis = new KobisOpenAPIRestService(kobis_KEY);
    public static void main(String[] args) throws Exception{
        KobisAPI kobisAPI = new KobisAPI();
        kobisAPI.byMovieNm("스크림").forEach(x -> {
            System.out.println(x);
        });
        System.out.println(kobisAPI.byMovieCd("20231843"));
    }

    public List<KobisMovieInfoResponse> byMovieNm(String str) throws Exception{
        //replace all special characters including ' ' from str
        String gluedTitle = str.replaceAll("[^a-zA-Z0-9\\s\u1100-\u11FF\u3130-\u318F\uAC00-\uD7AF\uA960-\uA97F]", "").replace(" ", "");
        Map<String, String> m4r = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        m4r.put("movieNm", gluedTitle);
        KobisMovieListResponse listResponse = om.readValue(kobis.getMovieList(true, m4r), KobisMovieListResponse.class);

        List<KobisMovieInfoResponse> resultList = new ArrayList<>();
        for(KobisMovieVO vo : listResponse.getMovieListResult().getMovieList()){
            m4r = new HashMap<>();
            m4r.put("movieCd", vo.getMovieCd());
            //System.out.println(kobis.getMovieInfo(true, m4r));
            resultList.add(om.readValue(kobis.getMovieInfo(true, m4r), KobisMovieInfoResponse.class));
        }

        return resultList;
    }

    public KobisMovieInfoResponse byMovieCd(String str) throws Exception{
        //replace all special characters including ' ' from str
        Map<String, String> m4r = new HashMap<>();
        ObjectMapper om = new ObjectMapper();

        m4r.put("movieCd", str);

        return om.readValue(kobis.getMovieInfo(true, m4r), KobisMovieInfoResponse.class);
    }

}
