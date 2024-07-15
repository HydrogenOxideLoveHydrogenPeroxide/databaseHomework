package com.shoppingMall.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.CustomException;
import com.shoppingMall.reggie.dto.ProductDto;
import com.shoppingMall.reggie.entity.Product;
import com.shoppingMall.reggie.entity.ProductParam;
import com.shoppingMall.reggie.mapper.ProductMapper;
import com.shoppingMall.reggie.service.ProductParamService;
import com.shoppingMall.reggie.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableTransactionManagement
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Autowired
    private ProductParamService productParamService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;

    /**
     *新增商品，同时保存对应的参数信息
     * @param productDto
     */
    @Transactional
    @Override
    public void saveWithParam(ProductDto productDto) {
        //保存商品的基本信息到商品表Product
        this.save(productDto);

        Long productDtoId = productDto.getId();

        //商品参数
        List<ProductParam> params = productDto.getParams();
        params.stream().map(item->{
            item.setProductId(productDtoId);
            return item;
        }).collect(Collectors.toList());
        //保存商品参数数据到商品参数表Product_Param
        productParamService.saveBatch(params);
    }

    /**
     * 根据id查询商品信息和对应的参数信息
     * @param id
     * @return
     */
    @Override
    public ProductDto getByIdWithParam(Long id) {
        //查询 商品基本信息 ，从product表查询
        Product product = this.getById(id);

        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product,productDto);

        //查询当前商品对应的口味信息，从product_param表查询
        LambdaQueryWrapper<ProductParam> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductParam::getProductId, product.getId());
        List<ProductParam> params = productParamService.list(queryWrapper);
        productDto.setParams(params);

        return productDto;
    }

    /**
     * 根据userId查询商品信息和对应的参数信息
     * @return
     */
    @Override
    public List<ProductDto> getByUserId() {
        long userId= BaseContext.getCurrentId();//获取当前用户id
        log.info("当前用户id={}",userId);
        //查询 商品基本信息 ，从product表查询
        LambdaQueryWrapper<Product> productqueryWrapper=new LambdaQueryWrapper<>();
        productqueryWrapper.eq(Product::getCreateUser, userId);
        List<Product> products =productService.list(productqueryWrapper);
        log.info(products.toString());

        List<ProductDto> productDtos = Collections.singletonList(new ProductDto());
        for(int i=0;i<products.toArray().length;i++){
            BeanUtils.copyProperties(products.get(i),productDtos.get(i));
            //查询当前商品对应的参数信息，从product_param表查询
            LambdaQueryWrapper<ProductParam> queryWrapper=new LambdaQueryWrapper<>();
            log.info("商品id{}",products.get(i).getId());
            queryWrapper.eq(ProductParam::getProductId, products.get(i).getId());
            List<ProductParam> params = productParamService.list(queryWrapper);
            productDtos.get(i).setParams(params);
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> getByNameWithParam(String name) {
        //查询 商品基本信息 ，从product表查询
        List<Product> products = this.getBaseMapper().selectList(new LambdaQueryWrapper<Product>().eq(Product::getName, name));

        List<ProductDto> productDtos = Collections.singletonList(new ProductDto());

        for(int i=0;i<products.toArray().length;i++){
            BeanUtils.copyProperties(products.get(i),productDtos.get(i));
            //查询当前商品对应的口味信息，从product_param表查询
            LambdaQueryWrapper<ProductParam> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(ProductParam::getProductId, products.get(i).getId());
            List<ProductParam> params = productParamService.list(queryWrapper);
            productDtos.get(i).setParams(params);
        }



        return productDtos;
    }

    @Override
    @Transactional
    public void updateWithFlavor(ProductDto productDto) {
        //更新product表的基本信息
        this.updateById(productDto);

        //清理当前商品对应的参数数据--product_params表的delete操作
        LambdaQueryWrapper<ProductParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductParam::getProductId,productDto.getId());

        productParamService.remove(queryWrapper);

        //添加当前提交过来的参数数据--product_param表进行insert操作
        List<ProductParam> flavors = productDto.getParams();
        flavors.stream().map(item->{
            item.setProductId(productDto.getId());
            return item;
        }).collect(Collectors.toList());
        productParamService.saveBatch(flavors);


    }

    /**
     *产品批量删除和单个删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {

        //构造条件查询器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //先查询该商品是否在售卖，如果是则抛出业务异常
        queryWrapper.in(ids!=null, Product::getId,ids);
        List<Product> list = this.list(queryWrapper);
        for (Product product : list) {
            Integer status = product.getStatus();
            //如果不是在售卖,则可以删除
            if (status != 1){
                this.removeById(product.getId());
            }else {
                //此时应该回滚,因为可能前面的删除了，但是后面的是正在售卖
                throw new CustomException("删除产品中有正在售卖产品,无法全部删除");
            }
        }

    }
}
