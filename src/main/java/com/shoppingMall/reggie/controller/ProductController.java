package com.shoppingMall.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.dto.ProductDto;
import com.shoppingMall.reggie.entity.Category;
import com.shoppingMall.reggie.entity.Product;
import com.shoppingMall.reggie.entity.ProductParam;
import com.shoppingMall.reggie.mapper.ProductMapper;
import com.shoppingMall.reggie.service.CategoryService;
import com.shoppingMall.reggie.service.ProductParamService;
import com.shoppingMall.reggie.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 商品管理
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductParamService productParamService;
    /**
     * 新增产品
     *
     * @param productDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody ProductDto productDto) {
        log.info(productDto.toString());
        productDto.setCreateUser(BaseContext.getCurrentId());
        productService.saveWithParam(productDto);
        //清理某个分类下面的菜品缓存数据
        Object key = redisTemplate.opsForValue().get("product_" + productDto.getCategoryId() + "_" + productDto.getStatus());
        cleanC();
        return R.success("新增产品成功");
    }

    private void cleanC() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    /**
     * 产品信息的分页
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Product> pageInfo = new Page<>(page, pageSize);
        Page<ProductDto> productDtoPage = new Page<>();


        //条件构造器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Product::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Product::getUpdateTime);

        //执行分页查询
        productService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, productDtoPage, "records");
        List<Product> records = pageInfo.getRecords();
        List<ProductDto> list = records.stream().map((item) -> {
            ProductDto productDto = new ProductDto();

            BeanUtils.copyProperties(item, productDto);
            Long categoryId = item.getCategoryId();//分类Id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                productDto.setCategoryName(categoryName);
            }
            return productDto;
        }).collect(Collectors.toList());
        productDtoPage.setRecords(list);
        return R.success(productDtoPage);
    }

    /**
     * 根据id查询商品信息和对应的参数信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<ProductDto> get(@PathVariable("id") Long id) {
        ProductDto productDto = productService.getByIdWithParam(id);
        return R.success(productDto);
    }

    /**
     * 根据名称查询商品信息和对应的参数信息
     *
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    public List<ProductDto> get(@PathVariable("name") String name) {
        return productService.getByNameWithParam(name);
    }

    /**
     * 修改产品
     *
     * @param productDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody ProductDto productDto) {
        log.info(productDto.toString());
        productService.updateWithFlavor(productDto);
        //清理所有商品缓存数据
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //清理某个分类下面的商品缓存数据
        String key = "product_" + productDto.getCategoryId() + "_" + productDto.getStatus();
        redisTemplate.delete(key);

        return R.success("修改产品成功");

    }

    /**
     * 根据条件查询对应的产品数据
     *
     * @param product
     * @return
     */
    @GetMapping("/list")
    public R<List<ProductDto>> list(Product product) {
        List<ProductDto> productDtoList = null;
        //动态构造key
        String key = "product_" + product.getCategoryId() + "_" + product.getStatus();//dish_12346564616163166_1

        //先从redis中获取缓存数据

        productDtoList = (List<ProductDto>) redisTemplate.opsForValue().get("key");
        //如果存在，直接返回，无需查询数据
        if (productDtoList != null) {
            //如果存在，就直接返回，无需查询数据库
            return R.success(productDtoList);
        }

        //构造查询条件
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(product.getCategoryId() != null, Product::getCategoryId, product.getCategoryId());

        //添加条件，查询条件为1(起售)
        queryWrapper.eq(Product::getStatus, 1);

        //添加排序条件
        queryWrapper.orderByAsc(Product::getSort).orderByDesc(Product::getUpdateTime);
        List<Product> list = productService.list(queryWrapper);

        productDtoList = list.stream().map((item) -> {
            ProductDto productDto = new ProductDto();

            BeanUtils.copyProperties(item, productDto);
            Long categoryId = item.getCategoryId();//分类Id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                productDto.setCategoryName(categoryName);
            }
            //当前菜品Id
            Long productId = item.getId();

            LambdaQueryWrapper<ProductParam> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ProductParam::getProductId, productId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<ProductParam> productParamList = productParamService.list(lambdaQueryWrapper);
            productDto.setParams(productParamList);
            return productDto;
        }).collect(Collectors.toList());

        //如果不存在，需要查询数据库，将查询到的菜品数据缓存到redis
        redisTemplate.opsForValue().set(key, productDtoList, 60, TimeUnit.MINUTES);

        return R.success(productDtoList);
    }

    /**
     * 对产品批量或者是单个 进行停售或者是起售
     * @return
     */
    @PostMapping("/status/{status}")
    //这个参数这里一定记得加注解才能获取到参数，否则这里非常容易出问题
    public R<String> status(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        //log.info("status:{}",status);
        //log.info("ids:{}",ids);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(ids != null, Product::getId, ids);
        //根据数据进行批量查询
        List<Product> list = productService.list(queryWrapper);

        for (Product product : list) {
            if (product != null) {
                product.setStatus(status);
                productService.updateById(product);
            }
        }
        return R.success("售卖状态修改成功");
    }

    /**
     * 产品批量删除和单个删除
     *
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        //删除菜品  这里的删除是逻辑删除
        productService.deleteByIds(ids);
        //删除菜品对应的口味  也是逻辑删除
        LambdaQueryWrapper<ProductParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ProductParam::getProductId, ids);
        productParamService.remove(queryWrapper);
        return R.success("产品删除成功");
    }

    /**
     * 根据userId查询对应的产品数据
     *
     * @return
     */
    @GetMapping("/mypage")
    public R<Page<ProductDto>> getMyProductList(int page, int pageSize) {
        //构造分页构造器对象
        Page<Product> pageInfo = new Page<>(page, pageSize);
        Page<ProductDto> productDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(Product::getCreateUser, BaseContext.getCurrentId());
        //添加排序条件
        queryWrapper.orderByDesc(Product::getUpdateTime);

        //执行分页查询
        productService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, productDtoPage, "records");
        List<Product> records = pageInfo.getRecords();
        List<ProductDto> list = records.stream().map((item) -> {
            ProductDto productDto = new ProductDto();

            BeanUtils.copyProperties(item, productDto);
            Long categoryId = item.getCategoryId();//分类Id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                productDto.setCategoryName(categoryName);
            }
            return productDto;
        }).collect(Collectors.toList());
        productDtoPage.setRecords(list);
        return R.success(productDtoPage);
    }

    /**
     * 修改产品
     * @param
     * @return
     */
    @PostMapping("/subStock")
    public R<String> subStock(long id,int stock) {
        log.info("id={},stock={}",id,stock);
        productMapper.subStock(id,stock);
        return R.success("修改产品成功");
    }
}
