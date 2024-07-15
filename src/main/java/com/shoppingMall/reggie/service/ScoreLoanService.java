package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.entity.ScoreLoan;

public interface ScoreLoanService extends IService<ScoreLoan> {
    void remove(long id);//删除

    void removeByuserId(long userId);//在用户注销的适合去掉所有

    boolean updateLoanUStatus(long id, int status);

    boolean passLoan(long id);

    boolean cancelLoan(long id);

    boolean onPassloan(long ids);
}
