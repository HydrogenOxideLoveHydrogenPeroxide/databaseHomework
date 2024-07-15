package com.shoppingMall.reggie.dto;

import com.shoppingMall.reggie.entity.OrderDetail;
import com.shoppingMall.reggie.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto extends Orders {

    private List<OrderDetail> orderDetails;
}
