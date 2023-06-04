package com.example.database.external.kobis.elements;

import com.example.database.external.kobis.elements.subs.KobisMovieInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KobisMovieInfoResult {
    @JsonProperty("movieInfo")
    KobisMovieInfo movieInfo;
}
