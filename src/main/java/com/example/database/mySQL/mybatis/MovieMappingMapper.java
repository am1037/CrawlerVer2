package com.example.database.mySQL.mybatis;


import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MovieMappingMapper {

    @Select("SELECT * FROM popcorn.movie_mapping")
    List<MovieMappingVO> selectAll();

    @Select("SELECT detail_url FROM popcorn.movie_mapping WHERE docid IS NULL")
    List<String> selectAllDOCIDNULL();

    @Select("SELECT detail_url FROM popcorn.movie_mapping WHERE my_title IS NULL")
    List<String> selectAllTitleNULL();

    @Select("SELECT detail_url FROM popcorn.movie_mapping WHERE kobis_movieCd IS NULL")
    List<String> selectAllMovieCdNULL();

    @Select("SELECT * FROM popcorn.movie_mapping WHERE detail_url = #{detail_url}")
    MovieMappingVO getMappingByUrl(@Param("detail_url") String detail_url);

    @Insert("INSERT INTO popcorn.movie_mapping (company, detail_url, my_id, my_title) VALUES (#{company}, #{detail_url}, #{my_id}, #{my_title})")
    int insertOne(MovieMappingVO url);

    @Update("UPDATE popcorn.movie_mapping SET docid = #{docid}, kmdb_url = #{kmdb_url}, posters = #{posters} WHERE detail_url = #{detail_url}")
    int updateDocidAndPosters(@Param("detail_url") String detail_url, @Param("docid") String docid, @Param("kmdb_url") String kmdb_url, @Param("posters") String posters);

    @Update("UPDATE popcorn.movie_mapping SET my_title = #{my_title} WHERE detail_url = #{detail_url}")
    int updateTitleByUrl(@Param("detail_url") String detail_url, @Param("my_title") String my_title);

    @Update("UPDATE popcorn.movie_mapping SET kobis_movieCd = #{movieMapping.kobis_movieCd} WHERE detail_url = #{movieMapping.detail_url}")
    int updateMovieCdByUrl(@Param("movieMapping") MovieMappingVO movieMapping);

//    나중에 mmm truncate 할 일이 있으면 씁시다!! 근데 쓸 일이 없으면 좋겟당 ㅎ
//    truncate 한 다음에 이걸 돌린 다음에 c3, c2를 쓰면 됩니당!
//    INSERT INTO popcorn.movie_mapping (detail_url)
//    SELECT DISTINCT detail_url FROM popcorn.movie_screen;
//    update popcorn.movie_mapping set company = "CGV" where company is null;



}