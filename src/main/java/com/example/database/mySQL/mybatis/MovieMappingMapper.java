package com.example.database.mySQL.mybatis;


import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MovieMappingMapper {

    @Select("SELECT * FROM popcorn.movie_mapping")
    List<MovieMapping> getAllMappings();

    @Select("SELECT * FROM popcorn.movie_mapping WHERE docid IS NULL")
    @MapKey("detail_url") // Specify the column to be used as the map key
    Map<String, MovieMapping> getAllMappingsIdIsNull();

    @Select("SELECT * FROM popcorn.movie_mapping WHERE detail_url = #{detail_url}")
    MovieMapping getMappingByUrl(@Param("detail_url") String detail_url);

    @Insert("INSERT INTO popcorn.movie_mapping (company, detail_url, my_id) VALUES (#{company}, #{detail_url}, #{my_id})")
    int insertUrl(MovieMapping url);

    @Update("UPDATE popcorn.movie_mapping SET docid = #{docid}, kmdb_url = #{kmdb_url} WHERE detail_url = #{detail_url}")
    int updateDocid(@Param("detail_url") String detail_url, @Param("docid") String docid, @Param("kmdb_url") String kmdb_url);

}