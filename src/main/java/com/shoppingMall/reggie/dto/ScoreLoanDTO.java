package com.shoppingMall.reggie.dto;

import lombok.Data;

@Data
public class ScoreLoanDTO {
    private int score;//想要借贷的积分数额
    private String loanReason;//借贷理由
}
