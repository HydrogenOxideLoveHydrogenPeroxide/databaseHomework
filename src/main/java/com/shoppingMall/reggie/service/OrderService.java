package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.entity.Orders;


public interface OrderService extends IService<Orders> {


    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

    public void submit2(Orders orders);

    public String subscore(int score);
}
