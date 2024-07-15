package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.entity.Score;

import java.util.List;

public interface ScoreService extends IService<Score> {
    List<Score> getScore(String id);
}
