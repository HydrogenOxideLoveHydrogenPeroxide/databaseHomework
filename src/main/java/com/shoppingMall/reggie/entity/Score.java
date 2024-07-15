package com.shoppingMall.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Score implements Serializable {
    private Long id;

    private Long userId;//相关联的用户id

    private String Name;//项目名称（积分借贷,任务获得，消费）

    private int scores;//积分的变化（+增加，-减少）

    private int scoresAfter;//修改完后的积分

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
