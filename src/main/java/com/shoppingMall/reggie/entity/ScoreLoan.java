package com.shoppingMall.reggie.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScoreLoan implements Serializable {
    private long id;
    private long userId;
    private int score;//想要借贷的积分数额
    private String loanReason;//借贷理由
    private int status;//0:待审核 1通过 2失败 3取消
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
