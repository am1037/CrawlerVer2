package com.example.database.mySQL.mybatis;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MovieMappingMapper {

    @Select("SELECT * FROM popcorn.movie_mapping")
    List<MovieMapping> getAllMappings();

    @Insert("INSERT INTO popcorn.movie_mapping (company, detail_url, my_id) VALUES (#{company}, #{detail_url}, #{my_id})")
    int insertUrl(MovieMapping url);
    // Define other methods as needed
}