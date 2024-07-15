package com.shoppingMall.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shoppingMall.reggie.dto.ProductDto;
import com.shoppingMall.reggie.entity.Product;

import java.util.List;

/* 商品的增删改查*/
public interface ProductService extends IService<Product> {

    //新增商品，同时插入商品对应的参数，需要操作两张表：product,product_param
    public void saveWithParam(ProductDto productDto);

    //根据id查询商品信息和对应服务的信息
    public ProductDto getByIdWithParam(Long id);

    //根据userId查询商品信息和对应服务的信息
    public List<ProductDto> getByUserId();

    //根据name查询商品信息和对应服务的信息
    public List<ProductDto> getByNameWithParam(String name);

    //更新商品信息，同时更新对应的参数信息
    public void updateWithFlavor(ProductDto dishDto);

    //根据传过来的id批量或者是单个的删除商品
    public void deleteByIds(List<Long> ids);
}
