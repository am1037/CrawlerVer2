package com.example.database.mySQL.mybatis;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ScreenMapper {
//    public class Screen {
//
//        String title;
//        String theater;
//        String screen;
//        String time;
//        String date;
//        String theater_id;
//        String movie_id;
//        Integer runtime;
//        String detail_url;
//
//    }

    @Select("SELECT * FROM popcorn.movie_screen where detail_url = #{detail_url}")
    List<ScreenVO> selectByUrl(@Param("detail_url") String detail_url);

    @Select("SELECT * FROM popcorn.movie_screen where theater = #{theater} and date = #{date}")
    List<ScreenVO> selectByTheaterAndDate(@Param("theater") String theater, @Param("date") String date);

    @Insert("INSERT INTO popcorn.movie_screen (title, company, screen_name, time, date, theater_id, runtime, detail_url, time_end) VALUES (#{title}, #{company}, #{screen_name}, #{time}, #{date}, #{theater_id}, #{runtime}, #{detail_url}, #{time_end})")
    void insert(ScreenVO screen);

//    @Update("UPDATE popcorn.movie_screen SET title = #{title}, company = #{company}, screen_name = #{screen_name}, time = #{time}, date = #{date}, theater_id = #{theater_id}, runtime = #{runtime}, time_end = #{time_end} WHERE detail_url = #{detail_url}")
//    void update(ScreenVO screen);

    @Select("SELECT * FROM popcorn.theater where theater_region = #{theater_region}")
    List<TheaterVO> selectByRegion(@Param("theater_region") String region);

    @Select("SELECT * FROM popcorn.movie_screen where movie_docid is null")
    List<ScreenVO> selectByDocidNull();

    @Select("SELECT * FROM popcorn.movie_screen where movieCd is null")
    List<ScreenVO> selectByMovieCdNull();

    @Update("UPDATE popcorn.movie_screen SET movie_docid = #{movie_docid} WHERE detail_url = #{detail_url}")
    int updateDocid(@Param("movie_docid") String movie_docid, @Param("detail_url") String detail_url);

    //update popcorn.movie_screen set movie_docid = substring(detail_url, -5)
    //temporary sql until kmdb is restored
}
