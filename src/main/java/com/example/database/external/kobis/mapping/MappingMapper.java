package com.example.database.external.kobis.mapping;

import com.example.database.external.kobis.KobisAPI;
import com.example.database.external.kobis.elements.KobisMovieInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MappingMapper {

    @Autowired
    KobisAPI kobisAPI;

    public String whichOne(MappingObject mo, List<KobisMovieInfoResponse> responses){
        if(responses.size()==1){
            return responses.get(0).getKobisMovieInfoResult().getMovieInfo().getMovieCd();
        }else{
            for(KobisMovieInfoResponse response : responses){
                if(response.getKobisMovieInfoResult().getMovieInfo().getShowTm().equals(mo.getRuntime())){
                    return response.getKobisMovieInfoResult().getMovieInfo().getMovieCd();
                }
            }
            responses.sort((o1, o2) -> {
                int o1Diff = Math.abs(o1.getKobisMovieInfoResult().getMovieInfo().getShowTm() - mo.getRuntime());
                int o2Diff = Math.abs(o2.getKobisMovieInfoResult().getMovieInfo().getShowTm() - mo.getRuntime());
                return o1Diff - o2Diff;
            });
            if(responses.get(0).getKobisMovieInfoResult().getMovieInfo().getAudits().get(0).getWatchGradeNm().equals(mo.getGrade())) {
                return responses.get(0).getKobisMovieInfoResult().getMovieInfo().getMovieCd();
            }
        }
        return "!NOT FOUND!";
    }

}
