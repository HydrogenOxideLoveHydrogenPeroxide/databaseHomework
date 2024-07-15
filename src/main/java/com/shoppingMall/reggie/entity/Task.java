package com.shoppingMall.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Task implements Serializable {
    private Long id;
    private String taskName;
    private String taskDes;
    private int score;
    private int status;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
