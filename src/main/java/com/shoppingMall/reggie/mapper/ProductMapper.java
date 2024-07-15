package com.shoppingMall.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shoppingMall.reggie.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    @Update("update product set stock=stock-#{stock} where  id=#{Id};")
    int subStock(@Param("Id") long id, @Param("stock") int stock);
}
