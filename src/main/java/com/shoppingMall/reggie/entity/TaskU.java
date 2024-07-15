package com.shoppingMall.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TaskU implements Serializable {
    private long id;
    private long userid;
    private String taskName;
    private long taskid;
    private int score;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
