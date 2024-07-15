package com.shoppingMall.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.entity.Score;
import com.shoppingMall.reggie.entity.User;
import com.shoppingMall.reggie.mapper.UserMapper;
import com.shoppingMall.reggie.service.ScoreService;
import com.shoppingMall.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*积分变更记录*/
@Slf4j
@RestController
@RequestMapping("/score")
public class ScoresController {
    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserMapper userMapper;

    //积分变化记录接口
    @GetMapping("/userScoreHistory")
    public R<Page<Score>> getUserScoreHistory(int page, int pageSize) {
        log.info("page:{},pageSize:{}", page, pageSize);
        Long userId = BaseContext.getCurrentId();

        // 分页构造器
         Page<Score> scoreHistorypageInfo = new Page<>(page, pageSize);

         LambdaQueryWrapper<Score> scoreQueryWrapper = new LambdaQueryWrapper<>();
         scoreQueryWrapper.in(Score::getUserId, userId);
         scoreQueryWrapper.orderByDesc(Score::getCreateTime);

         // 进行分页查询
         scoreService.page(scoreHistorypageInfo, scoreQueryWrapper);

         return R.success(scoreHistorypageInfo);
    }

    //积分变化记录接口
    @GetMapping("/ScoreHistoryByuserId")
    public R<Page<Score>> getCetainUserScoreHistory(int page, int pageSize,long id) {
        log.info("page:{},pageSize:{}", page, pageSize);
        Long userId = id;

        // 分页构造器
        Page<Score> scoreHistorypageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Score> scoreQueryWrapper = new LambdaQueryWrapper<>();
        scoreQueryWrapper.in(Score::getUserId, userId);
        scoreQueryWrapper.orderByDesc(Score::getCreateTime);

        // 进行分页查询
        scoreService.page(scoreHistorypageInfo, scoreQueryWrapper);

        return R.success(scoreHistorypageInfo);
    }

    //积分排行榜接口
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        //分页构造器对象
        Page<User> pageInfo = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(User::getScores);

        userService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    //根据电话号获得用户的积分数据
    @GetMapping("/userScore")
    public R<List<User>> getUserScore(String phoneNumber){
        System.out.println(phoneNumber);
        List<User> score =  userService.getScore(phoneNumber);
        return R.success(score);
    }
}
