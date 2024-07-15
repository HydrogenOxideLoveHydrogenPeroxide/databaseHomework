package com.shoppingMall.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shoppingMall.reggie.common.CustomException;
import com.shoppingMall.reggie.entity.Category;
import com.shoppingMall.reggie.entity.Product;
import com.shoppingMall.reggie.mapper.CategoryMapper;
import com.shoppingMall.reggie.service.CategoryService;
import com.shoppingMall.reggie.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{


    @Autowired
    private ProductService productService;

    /**
     * 根据id删除分类,删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Product> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类ID进行查询
        productLambdaQueryWrapper.eq(Product::getCategoryId,id);
        int count1 = (int) productService.count(productLambdaQueryWrapper);
        if(count1>0){
            throw new CustomException("当前分类下关联了产品，不能删除");//已经关联了产品，如果已关联，抛出一个业务异常
        }

        //正常删除分类
        super.removeById(id);
    }
}
