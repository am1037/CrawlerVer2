package com.example.crawler.lotte.elements;

import com.example.crawler.MyCrawlingResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LotteMovieDetailCrawlResult extends MyCrawlingResult {
    String url;

    @JsonProperty("Movie")
    Movie movie;

    @JsonProperty("Casting")
    Casting casting;

    /*
    "Movie": {
    "MakingNationNameKR": "미국",
    "MakingNationNameUS": "미국",
    "MakingNationName": null,
    "MovieGenreCode": "190",
    "PlayTime": 135,
    "SoundTypeCode": "100,300",
    "SoundTypeNameKR": "일반사운드,Dolby Atmos",
    "SoundTypeNameUS": "일반사운드,Dolby Atmos",
    "FourDTypeCode": "100",
    "FourDTypeNameKR": "일반",
    "FourDTypeNameUS": "일반",
    "TranslationDivisionCode": "50",
    "TranslationDivisionNameKR": "더빙",
    "TranslationDivisionNameUS": "더빙",
    "HomepageURL": "",
    "SynopsisKR": "<b> “내 안의 목소리를 따라<br>자유롭게 꿈꾸고 사랑할 거야” </b><br><br>아틀란티카 바다의 왕 ‘트라이튼’의 사랑스러운 막내딸인 인어 ‘에리얼’은<br>늘 인간들이 사는 바다 너머 세상으로의 모험을 꿈꾼다.<br><br>어느 날, 우연히 바다 위로 올라갔다가<br>폭풍우 속 가라앉는 배에 탄 인간 ‘에릭 왕자’의 목숨을 구해준다.<br>갈망하던 꿈과 운명적인 사랑을 이루기 위해 용기를 낸 ‘에리얼’은<br>사악한 바다 마녀 ‘울슐라’와의 위험한 거래를 통해 다리를 얻게 된다.<br><br>드디어 바다를 벗어나 그토록 원하던 인간 세상으로 가게 되지만,<br>그 선택으로 ‘에리얼’과 아틀란티카 왕국 모두 위험에 처하게 되는데…<br><br><b> 바닷속, 그리고 그 너머<br>아름다운 꿈과 사랑의 멜로디가 펼쳐진다! </b>",
    "SynopsisUS": "<b> “내 안의 목소리를 따라<br>자유롭게 꿈꾸고 사랑할 거야” </b><br><br>아틀란티카 바다의 왕 ‘트라이튼’의 사랑스러운 막내딸인 인어 ‘에리얼’은<br>늘 인간들이 사는 바다 너머 세상으로의 모험을 꿈꾼다.<br><br>어느 날, 우연히 바다 위로 올라갔다가<br>폭풍우 속 가라앉는 배에 탄 인간 ‘에릭 왕자’의 목숨을 구해준다.<br>갈망하던 꿈과 운명적인 사랑을 이루기 위해 용기를 낸 ‘에리얼’은<br>사악한 바다 마녀 ‘울슐라’와의 위험한 거래를 통해 다리를 얻게 된다.<br><br>드디어 바다를 벗어나 그토록 원하던 인간 세상으로 가게 되지만,<br>그 선택으로 ‘에리얼’과 아틀란티카 왕국 모두 위험에 처하게 되는데…<br><br><b> 바닷속, 그리고 그 너머<br>아름다운 꿈과 사랑의 멜로디가 펼쳐진다! </b>",
    "Synopsis": null,
    "TotalViewCount": 5502,
    "AgePrefer10": "8.8",
    "AgePrefer20": "37.3",
    "AgePrefer30": "31.8",
    "AgePrefer40": "22.1",
    "ManPrefer": "26.5",
    "WomanPrefer": "73.5",
    "KOFCustCnt": 496139,
    "AggrDt": "2023-06-02 오전 12:00:00",
    "MakingNationNameKR2": "",
    "MakingNationNameKR3": "",
    "MovieGenreNameKR2": "가족",
    "MovieGenreNameKR3": "판타지",
    "Active": 0,
    "RepresentationMovieCode": "19874",
    "MoviePlayYN": "Y",
    "MoviePlayEndYN": 0,
    "MovieNameKR": "인어공주",
    "MovieNameUS": "인어공주",
    "MovieName": null,
    "PosterURL": "http://caching.lottecinema.co.kr//Media/MovieFile/MovieImg/202305/19874_103_1.jpg",
    "ViewGradeCode": 0,
    "ViewGradeNameKR": "전체관람가",
    "ViewGradeNameUS": "전체관람가",
    "ViewGradeName": null,
    "BookingRate": 1.54,
    "ReleaseDate": "2023-05-24 오전 12:00:00",
    "DDay": null,
    "ExpectEvaluation": 0.0,
    "ViewEvaluation": 8.0,
    "Evaluation": 0.0,
    "BookingYN": "Y",
    "ViewRate": 1.54,
    "SpecialScreenDivisionCode": [
      "200",
      "400",
      "950",
      "960",
      "986",
      "987"
    ],
    "SoloOpenYN": null,
    "OpenORClosing": 0,
    "BookingRank": "4",
    "ViewSortSequence": null,
    "BookingSortSequence": null,
    "FilmCode": "200",
    "FilmName": "2D",
    "MovieFestivalID": 0,
    "DirectorName": null,
    "ActorName": null,
    "MovieGenreNameKR": "뮤지컬",
    "MovieGenreNameUS": "뮤지컬",
    "MovieGenreName": null,
    "ProductionCompanyName": "",
    "MovieDivisionCode": null,
    "MovieFestivalName": null,
    "MovieFestivalFilmCount": null,
    "MovieFestivalOpenDate": null,
    "MovieFestivalFinalDate": null,
    "MovieFestivalOpenMovieCode": null,
    "MovieFestivalOpenMovieName": null,
    "MovieFestivalFinalMovieCode": null,
    "MovieFestivalFinalMovieName": null,
    "ImagePath": null,
    "ImageALT": null,
    "LinkDivisionCode": null,
    "ParameterEventID": null,
    "ParameterRepMovieCode": null,
    "URL": null,
    "PopupTitle": null,
    "KOFMovieCd": null,
    "PlanedRelsYN": 1,
    "PlanedRelsMnth": "2023-05",
    "KeywordID": null,
    "KeywordNm": null,
    "MoreLookCD": null,
    "MoreLookUrl": null,
    "MoreLookImgUrl": null,
    "MoreLookImgAlt": null,
    "UpdateYn": null,
    "ArrayStandardCd": null,
    "UpdateDt": null,
    "LikeYN": "N",
    "ViewCount": 0,
    "LikeCount": 528,
    "TargetMovieListCode": 0,
    "ViewCountSortSequence": 0,
    "PlayDt": null,
    "KeywordGroupID": 0,
    "KeywordGroupName": null
  }
     */
}
