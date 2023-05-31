package com.example.database.external.kmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KmdbMovieSimpleInfoResponseVO {
    @JsonProperty("Query")
    private String query;
    @JsonProperty("KMAQuery")
    private String kmaQuery;
    @JsonProperty("TotalCount")
    private int totalCount;
    @JsonProperty("Data")
    private List<Collection> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Collection {
        @JsonProperty("CollName")
        private String collName;
        @JsonProperty("TotalCount")
        private int totalCount;
        @JsonProperty("Count")
        private int count;
        @JsonProperty("Result")
        private List<Movie> result;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Movie {
            @JsonProperty("DOCID")
            private String DOCID;
            private String movieId;
            private String movieSeq;
            private String title;
            private String titleEng;
            private String titleOrg;
            private String titleEtc;
            private String prodYear;
            private Directors directors;
            private Actors actors;
            private String nation;
            private String company;
            private Plots plots;
            private String runtime;
            private String rating;
            private String genre;
            private String kmdbUrl;
            private String posters;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Directors {
                private List<Director> director;

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Director {
                    private String directorNm;
                    private String directorEnNm;
                    private String directorId;
                }
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Actors {
                private List<Actor> actor;

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Actor {
                    private String actorNm;
                    private String actorEnNm;
                    private String actorId;
                }
            }

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Plots {
                private List<Plot> plot;

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Plot {
                    private String plotLang;
                    private String plotText;
                }
            }
        }
    }

    public void printAsJson(String path){
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(new File(path), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KmdbMovieSimpleInfoResponseVO readFromJson(String path){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(new File(path), KmdbMovieSimpleInfoResponseVO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Collection.Movie> getMovies(int i){
        return data.get(i).getResult();
    }
}