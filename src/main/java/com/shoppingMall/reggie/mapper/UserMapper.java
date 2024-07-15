package com.shoppingMall.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shoppingMall.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where  phone=#{phoneNum};")
    List<User> getUserByPhoneNum(@Param("phoneNum") String phoneNum);

    @Select("select * from user where  id=#{userId};")
    List<User> getUserById(@Param("userId") Long userId);

    @Select("select * from user where  phone=#{phoneNum};")
    List<User> getScore(@Param("phoneNum") String phoneNum);

    @Update("update user set scores=scores-#{score} where  id=#{userId};")
    int sub_score(@Param("score") int score,@Param("userId") Long userId);

    @Update("update user set scores=scores+#{score} where  id=#{userId};")
    int add_score(@Param("score") int score,@Param("userId") Long userId);

    @Update("update user set scores=scores + #{amount} where  id=#{userId};")
    int add(@Param("userId") Long userId,@Param("amount") BigDecimal amount);
}
