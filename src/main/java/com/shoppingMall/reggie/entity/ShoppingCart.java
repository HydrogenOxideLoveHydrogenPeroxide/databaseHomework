package com.shoppingMall.reggie.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
public class ShoppingCart implements Serializable {
    private Long id;
    private String name;//名称
    private Long userId;//用户id
    private Long productId;//产品id
    private String productParam;//参数
    private int number;//数量
    private int amount;//积分数额
    private String image; //图片
    private LocalDateTime createTime;
}
