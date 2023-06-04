package com.example.crawler.lotte.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaySeqs {

    @JsonProperty("Items")
    List<ItemScreen> itemScreens;

    @JsonProperty("ItemCount")
    Integer itemCount;

}
