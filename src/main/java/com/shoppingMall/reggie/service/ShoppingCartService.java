package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    void clean();
}
