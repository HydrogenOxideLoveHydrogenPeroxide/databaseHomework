package com.shoppingMall.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.dto.ScoreLoanDTO;
import com.shoppingMall.reggie.entity.ScoreLoan;
import com.shoppingMall.reggie.service.ScoreLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/scoreLoan")
public class ScoreLoanController {

    @Autowired
    private ScoreLoanService scoreLoanService;

    /**
     * 发布新贷款请求
     * @param scoreLoanDTO
     * @return R
     */
    @PostMapping
    public R InsertLoan(@RequestBody ScoreLoanDTO scoreLoanDTO){
        log.info("ScoreLoan:{}",scoreLoanDTO);
        ScoreLoan scoreLoan = new ScoreLoan();
        BeanUtils.copyProperties(scoreLoanDTO,scoreLoan);
        scoreLoan.setUserId(BaseContext.getCurrentId());//设置为待审核
        scoreLoan.setStatus(0);//设置为待审核
        scoreLoan.setCreateTime(LocalDateTime.now());
        scoreLoan.setUpdateTime(LocalDateTime.now());
        scoreLoanService.save(scoreLoan);
        return R.success("贷款发布成功！");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")//管理端显示的所有用户的贷款申请
    public R<Page> page(int page, int pageSize){
        log.error("page={},pageSize={}",page,pageSize);
        //分页构造器
        Page<ScoreLoan> pageInfo=new Page<>(page,pageSize);
        //条件构造器对象
        LambdaQueryWrapper<ScoreLoan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ScoreLoan::getStatus,0);//已取消的查不到
        queryWrapper.orderByDesc(ScoreLoan::getCreateTime);

        //进行分页查询
        scoreLoanService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/mypage")//当前用户的贷款申请
    public R<Page> mypage(int page, int pageSize){
        Long userId = BaseContext.getCurrentId();
        log.info("page:{},pageSize:{},userId:{}", page, pageSize,userId);

        //分页构造器
        Page<ScoreLoan> pageInfo=new Page<>(page,pageSize);
        //条件构造器对象
        LambdaQueryWrapper<ScoreLoan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ScoreLoan::getUserId, userId);
        queryWrapper.orderByDesc(ScoreLoan::getCreateTime);

        //进行分页查询
        scoreLoanService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除贷款
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(long id){
        scoreLoanService.remove(id);
        return R.success("贷款删除成功");
    }

    /**
     * 根据id取消贷款
     * @param id
     * @return
     */
    @PutMapping("/statusUpdate")
    public R<String> cancel(long id){
        log.info("id={}",id);
        scoreLoanService.cancelLoan(id);
        return R.success("贷款取消成功");
    }

    /**
     * 根据id修改贷款信息
     * @param scoreLoanDTO
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody ScoreLoanDTO scoreLoanDTO){
        log.info("修改贷款信息:",scoreLoanDTO);
        ScoreLoan scoreLoan = new ScoreLoan();
        BeanUtils.copyProperties(scoreLoanDTO,scoreLoan);
        scoreLoan.setUpdateTime(LocalDateTime.now());
        scoreLoanService.updateById(scoreLoan);
        return R.success("修改任务信息成功");
    }

    /**
     * 根据id修改贷款信息
     * @param id
     * @return
     */
    @PutMapping("/passloan")
    public R<String> pass(long id){
        scoreLoanService.passLoan(id);
        return R.success("通过贷款成功");
    }

    @PutMapping("/rejectloan")
    public R<String> reject(long id){
        scoreLoanService.onPassloan(id);
        return R.success("拒绝贷款成功");
    }
}
