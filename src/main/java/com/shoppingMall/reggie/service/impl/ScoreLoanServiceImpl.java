package com.shoppingMall.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shoppingMall.reggie.entity.ScoreLoan;
import com.shoppingMall.reggie.entity.User;
import com.shoppingMall.reggie.mapper.ScoreLoanMapper;
import com.shoppingMall.reggie.mapper.ScoreMapper;
import com.shoppingMall.reggie.mapper.UserMapper;
import com.shoppingMall.reggie.service.ScoreLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScoreLoanServiceImpl extends ServiceImpl<ScoreLoanMapper, ScoreLoan> implements ScoreLoanService {
    @Autowired
    private ScoreLoanMapper scoreLoanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public void remove(long id) {
        super.removeById(id);
    }

    @Override
    public void removeByuserId(long userId) {
        LambdaQueryWrapper<ScoreLoan> scoreLoanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类ID进行查询
        scoreLoanLambdaQueryWrapper.eq(ScoreLoan::getUserId,userId);
        this.remove(scoreLoanLambdaQueryWrapper);
    }

    @Override
    public boolean updateLoanUStatus(long id, int status) {
        // 查询 TaskU 记录
        LambdaQueryWrapper<ScoreLoan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScoreLoan::getId, id);
        ScoreLoan scoreLoan = scoreLoanMapper.selectOne(queryWrapper);
        if (scoreLoan!=null) {
            scoreLoan.setStatus(status);
        }
        // 更新数据库记录
        return scoreLoanMapper.updateById(scoreLoan) > 0;
    }

    @Override
    public boolean passLoan(long id) {
        if(updateLoanUStatus(id,1)){//如果借贷成功
            LambdaQueryWrapper<ScoreLoan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ScoreLoan::getId, id);
            ScoreLoan scoreLoan = scoreLoanMapper.selectOne(queryWrapper);
            userMapper.add_score(scoreLoan.getScore(),scoreLoan.getUserId());//修改用户表
            User user=userMapper.getUserById(scoreLoan.getUserId()).get(0);//获取用户
            scoreMapper.addScoreHisByid(scoreLoan.getUserId(),
                    "积分借贷(id:"+scoreLoan.getId()+")",
                    scoreLoan.getScore(),
                    user.getScores());
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean cancelLoan(long id) {//取消贷款
        if(updateLoanUStatus(id,3)){//如果取消成功
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean onPassloan(long id) {
        if(updateLoanUStatus(id,2)){//如果取消成功
            return true;
        }
        else{
            return false;
        }
    }
}
