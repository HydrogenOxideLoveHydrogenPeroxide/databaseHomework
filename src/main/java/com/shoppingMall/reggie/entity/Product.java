package com.shoppingMall.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/*商品*/
@Data
public class Product implements Serializable {

    private Long id;

    private String name;//商品名称

    private Long categoryId;//商品分类id

    private int price;//商品价格

    private int stock;//库存

    private String image;//图片

    private String description;//描述信息

    private int status;//0 停售 1 起售 2 待审核（用户提交商品） 3 拒绝（用户提交商品）

    private Integer sort;//顺序


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    private Integer isDeleted;//是否删除
}
