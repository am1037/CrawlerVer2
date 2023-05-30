package com.example.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class MyCrawlingResult {
    public String toJsonString(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "error in toJsonString()";
        }
    }
}
