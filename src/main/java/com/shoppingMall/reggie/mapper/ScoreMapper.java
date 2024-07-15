package com.shoppingMall.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shoppingMall.reggie.entity.Score;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

    //查询某个用户的积分
    @Select("select * from score where user_id=#{userId};")
    List<Score> getScoreByid(@Param("userId") BigInteger userId);

    //插入积分历史
    @Insert("INSERT INTO score (user_id, name ,scores ,scores_after) VALUES (#{userId}, #{name} ,#{score} ,#{scoreAfter});")
    int addScoreHisByid(@Param("userId") long userId, @Param("name") String name, @Param("score") int score, @Param("scoreAfter") int scoreAfter);

    //删除某个用户的所有积分记录
    @Delete("DELETE from score where user_id=#{userId}")
    int deleteScoreByuserId(@Param("userId") long userId);
}
