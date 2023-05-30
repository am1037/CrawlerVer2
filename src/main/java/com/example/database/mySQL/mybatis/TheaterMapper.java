package com.example.database.mySQL.mybatis;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface TheaterMapper {

    @Select("SELECT * FROM popcorn.theater WHERE theater_company = #{theater_company} AND theater_id = #{theater_id}")
    Theater getTheaterById(@Param("theater_company") String theater_company, @Param("theater_id") String theater_id);


}