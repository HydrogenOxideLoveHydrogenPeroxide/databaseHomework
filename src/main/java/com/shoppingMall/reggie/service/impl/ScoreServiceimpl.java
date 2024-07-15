package com.shoppingMall.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shoppingMall.reggie.entity.Score;
import com.shoppingMall.reggie.mapper.ScoreMapper;
import com.shoppingMall.reggie.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class ScoreServiceimpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> getScore(String id) {
        return scoreMapper.getScoreByid(new BigInteger(id));
    }
}
