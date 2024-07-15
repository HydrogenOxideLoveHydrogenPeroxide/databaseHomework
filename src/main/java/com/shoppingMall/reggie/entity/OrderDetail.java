package com.shoppingMall.reggie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
public class OrderDetail implements Serializable {

    private Long id;

    //名称
    private String name;

    //订单id
    private Long orderId;


    //产品id
    private Long productId;


    //产品参数
    private String productParam;


    //数量
    private Integer number;

    //金额
    private int amount;

    //图片
    private String image;
}
