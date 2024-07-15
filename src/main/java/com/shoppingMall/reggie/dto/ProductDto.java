package com.shoppingMall.reggie.dto;

import com.shoppingMall.reggie.entity.Product;
import com.shoppingMall.reggie.entity.ProductParam;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto extends Product {

    private List<ProductParam> params = new ArrayList<>();

    private String categoryName;

    private Long createUser;

 ;   private Integer copies;
}
