package com.shoppingMall.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.entity.ShoppingCart;
import com.shoppingMall.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        Long currentId = BaseContext.getCurrentId();//设置用户id,指定当前是哪个用户的购物车数据
        shoppingCart.setUserId(currentId);
        Long productId = shoppingCart.getProductId();//查询当前产品是否在购物车中

        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        if(productId!=null){
            queryWrapper.eq(ShoppingCart::getProductId,productId);
        }

        log.info("搜索内容:currentId={},productId={}",currentId,productId);
        //SQL:select * from shopping_cart where user_id = ? and product_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        log.info("购物车数据:{}",cartServiceOne);

        if(cartServiceOne!=null){
            //如果已经存在，就在原来数量基础上加一
            int number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //如果不存在，则添加购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setProductId(productId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne=shoppingCart;
        }
        log.info("cartServiceOne:{}",cartServiceOne);
        return R.success(cartServiceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车...");
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //SQL:delete from shopping_cart where user_id = ?

        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }

    /**
     *客户端的产品数量减少设置
     *没必要设置返回值
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    @Transactional
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {

        log.info("{}",shoppingCart);
        Long productId = shoppingCart.getProductId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //代表数量减少的是菜品数量
        if (productId != null) {
            //通过dishId查出购物车对象
            //这里必须要加两个条件，否则会出现用户互相修改对方与自己购物车中相同套餐或者是菜品的数量
            queryWrapper.eq(ShoppingCart::getProductId, productId).eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            ShoppingCart cart1 = shoppingCartService.getOne(queryWrapper);
            cart1.setNumber(cart1.getNumber() - 1);
            Integer LatestNumber = cart1.getNumber();
            if (LatestNumber > 0) {
                //对操作的数据进行更新
                shoppingCartService.updateById(cart1);
            } else if (LatestNumber == 0) {
                //如果购物车的菜品数量减为0，那么就把菜品从购物车删除
                shoppingCartService.removeById(cart1.getId());
            } else if (LatestNumber < 0) {
                return R.error("操作异常");
            }
            return R.success(cart1);

        }
        //如果两个大if判断都进不去
        return R.error("操作异常");
    }
}
