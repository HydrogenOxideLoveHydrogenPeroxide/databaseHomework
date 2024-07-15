package com.shoppingMall.reggie.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String taskName;
    private String taskDes;
    private Integer score;
    //结束时间
    private LocalDateTime endTime;

}
