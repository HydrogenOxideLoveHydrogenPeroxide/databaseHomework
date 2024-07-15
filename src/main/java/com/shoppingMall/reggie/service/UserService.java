package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getScore(String phoneNum);
}
