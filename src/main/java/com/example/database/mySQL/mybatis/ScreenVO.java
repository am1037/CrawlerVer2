package com.example.database.mySQL.mybatis;

import lombok.Data;

@Data
public class ScreenVO {

    String title;
    String company;
    String screen_name;
    String time;
    String date;
    String theater_id;
    String movie_id;
    Integer runtime;
    String detail_url;
    String time_end;

}
