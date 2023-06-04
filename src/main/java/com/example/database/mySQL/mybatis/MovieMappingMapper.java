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

    @Select("SELECT * FROM popcorn.movie_mapping WHERE my_title IS NULL")
    @MapKey("detail_url") // Specify the column to be used as the map key
    Map<String, MovieMapping> getAllMappingsTitleIsNull();


    @Select("SELECT * FROM popcorn.movie_mapping WHERE detail_url = #{detail_url}")
    MovieMapping getMappingByUrl(@Param("detail_url") String detail_url);

    @Insert("INSERT INTO popcorn.movie_mapping (company, detail_url, my_id, my_title) VALUES (#{company}, #{detail_url}, #{my_id}, #{my_title})")
    int insertUrl(MovieMapping url);

    @Update("UPDATE popcorn.movie_mapping SET docid = #{docid}, kmdb_url = #{kmdb_url}, posters = #{posters} WHERE detail_url = #{detail_url}")
    int updateDocidAndPosters(@Param("detail_url") String detail_url, @Param("docid") String docid, @Param("kmdb_url") String kmdb_url, @Param("posters") String posters);

    @Update("UPDATE popcorn.movie_mapping SET my_title = #{my_title} WHERE detail_url = #{detail_url}")
    int updateTitleByUrl(@Param("detail_url") String detail_url, @Param("my_title") String my_title);

//    나중에 mmm truncate 할 일이 있으면 씁시다!! 근데 쓸 일이 없으면 좋겟당 ㅎ
//    truncate 한 다음에 이걸 돌린 다음에 c3, c2를 쓰면 됩니당!
//    INSERT INTO popcorn.movie_mapping (detail_url)
//    SELECT DISTINCT detail_url FROM popcorn.movie_screen;
//    update popcorn.movie_mapping set company = "CGV" where company is null;



}