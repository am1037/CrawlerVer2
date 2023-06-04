package com.example.database.external.kobis.elements.subs;

import com.example.database.external.kobis.elements.subs.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KobisMovieInfo {

    String movieCd;
    String movieNm;
    String movieNmEn;
    String movieNmOg;
    Integer showTm;
    String prdtYear;
    String openDt;
    String prdtStatNm;
    String typeNm;
    List<Nation> nations;
    List<Genre> genres;
    List<People> directors;
    List<People> actors;
    List<ShowType> showTypes;
    @JsonProperty("companys")
    List<Company> companies;
    List<Audit> audits;
    List<People> staffs;

}
