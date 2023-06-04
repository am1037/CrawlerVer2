package com.example.database.external.kobis.elements;

import lombok.Data;

import java.util.List;

@Data
public class KobisMovieVO {

//    movieCd	문자열	영화코드를 출력합니다. ㅇ
//    movieNm	문자열	영화명(국문)을 출력합니다. ㅇ
//    movieNmEn	문자열	영화명(영문)을 출력합니다.
//    prdtYear	문자열	제작연도를 출력합니다. 
//    openDt	문자열	개봉일을 출력합니다. ㅇ
//    typeNm	문자열	영화유형을 출력합니다.
//    prdtStatNm	문자열	제작상태를 출력합니다.
//    nationAlt	문자열	제작국가(전체)를 출력합니다.
//    genreAlt	문자열	영화장르(전체)를 출력합니다. ㅇ
//    repNationNm	문자열	대표 제작국가명을 출력합니다.
//    repGenreNm	문자열	대표 장르명을 출력합니다. ㅇ
//    directors	문자열	영화감독을 나타냅니다. ㅇ
//    peopleNm	문자열	영화감독명을 출력합니다.
//    companys	문자열	제작사를 나타냅니다.
//    companyCd	문자열	제작사 코드를 출력합니다.
//    companyNm	문자열	제작사명을 출력합니다.

    String movieCd;
    String movieNm;
    String movieNmEn;
    String prdtYear;
    String openDt;
    String typeNm;
    String prdtStatNm;
    String nationAlt;
    String genreAlt;
    String repNationNm;
    String repGenreNm;
    List<People> directors; // 얘 배열임
//    String peopleNm;
    List<Company> companys; // 얘 배열임
//    String companyCd;
//    String companyNm;

    @Data
    static
    class People{
        String peopleNm; 
    }
    @Data
    static
    class Company{
        String companyCd;
        String companyNm;
    }
}
