package com.shoppingMall.reggie.entity;

import lombok.Data;

import java.io.Serializable;

//用户信息
//登录方式：手机号+验证码
@Data
public class User implements Serializable {
    private Long id;
    private String name;//姓名
    private String phone;//手机号
    private String sex;//性别 0 女 1 男
    private String avatar;//头像
    private int status;//状态 0:禁用，1:正常
    private int scores;//积分
}
