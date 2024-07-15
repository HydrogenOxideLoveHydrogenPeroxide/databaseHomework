package com.shoppingMall.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shoppingMall.reggie.common.BaseContext;
import com.shoppingMall.reggie.common.R;
import com.shoppingMall.reggie.entity.*;
import com.shoppingMall.reggie.mapper.ScoreMapper;
import com.shoppingMall.reggie.service.*;
import com.shoppingMall.reggie.service.impl.ShoppingCartServiceImpl;
import com.shoppingMall.reggie.utils.ValidateCodeUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private ScoreLoanService scoreLoanService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
    /**
     * 发送手机短信验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) throws Exception {
        //获取手机号
        String phone=user.getPhone();
        log.info("用户的电话号码为{}",phone);

        //判断电话号是否为空
        if(StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();//生成随机四位验证码
            String codeFinal = "{\"code\":"+code +"}";
            session.setAttribute(phone,code);//需要将生成的验证码保存到Session

            //将生成的验证码缓存到redis中，有效期为5分钟
//            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);


            //阿里云短信服务
//            com.aliyun.dysmsapi20170525.Client client = UserController.createClient(" LTAI5t6yJtNVMhuGHn2Kk692", "nxYexxUO9kjPrPJK4ujChBZLMDkZbH");
//            com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
//                    .setSignName("shopSystem")
//                    .setTemplateCode("SMS_461040738")
//                    .setPhoneNumbers(phone)
//                    .setTemplateParam(codeFinal);
//            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
//            com.aliyun.dysmsapi20170525.models.SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
//            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));

            log.info("code={}",code);
            return R.success("手机验证码短信发送成功");

        }

        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){

        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);

        //从redis中获取缓存的验证码
//        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对(页面提交的验证码和Session中保存的验证码比对)
        if(codeInSession!=null&&codeInSession.equals(code)) {
            //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user==null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                user.setScores(0);//每个用户初始积分为0
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //如果用户登录成功则删除Redis中缓存的验证码
//            redisTemplate.delete(phone);
            return R.success(user);
        }

        return R.error("登录失败");
    }

    /**
     * ①在controller中创建对应的处理方法来接受前端的请求，请求方式为post；
     * ②清理session中的用户id
     * ③返回结果（前端页面会进行跳转到登录页面）
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request){
        request.removeAttribute("user");
        return R.success("退出成功");
    }

    @PostMapping("/logoff")//注销
    public R<String> logoff(HttpServletRequest request,String phoneNumber){
        log.info("注销用户{}，启动！！！！",phoneNumber);


        //移除Address表中数据
        LambdaQueryWrapper<AddressBook> AddrqueryWrapper = new LambdaQueryWrapper<>();
        AddrqueryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        addressBookService.remove(AddrqueryWrapper);
        //移除Score表中数据
        scoreMapper.deleteScoreByuserId(BaseContext.getCurrentId());
        //移除ScoreLoan中数据
        LambdaQueryWrapper<ScoreLoan> LoanqueryWrapper = new LambdaQueryWrapper<>();
        LoanqueryWrapper.eq(ScoreLoan::getUserId, BaseContext.getCurrentId());
        scoreLoanService.remove(LoanqueryWrapper);
        //删除orders表中数据
        LambdaQueryWrapper<Orders> OrderqueryWrapper = new LambdaQueryWrapper<>();
        OrderqueryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        orderService.remove(OrderqueryWrapper);
        //删除上架Product
        LambdaQueryWrapper<Product> productQueryWrapper=new LambdaQueryWrapper<>();
        productQueryWrapper.eq(Product::getCreateUser,BaseContext.getCurrentId());
        productService.remove(productQueryWrapper);
        //删除ShoppingCart相关部分
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper=new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);

        //移除User表中数据
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        userService.remove(queryWrapper.eq(User::getPhone,phoneNumber));

        //退出
        request.removeAttribute("user");


        return R.success("注销成功");
    }

    @GetMapping("/page")
    public R<Page<User>> page(int page, int pageSize, String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page<User> pageInfo=new Page<User>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();

        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),User::getName,name);
        //排序条件
        queryWrapper.orderByDesc(User::getPhone);

        //执行查询
        userService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @GetMapping("/id/{id}")
    public R<User> getById(@PathVariable Long id){
        log.info("根据id查询信息...");
        User user = userService.getById(id);
        if(user!=null){
            return R.success(user);
        }
        return R.error("没有查询到对应用户信息");
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody User user){
        log.info(user.toString());

        long id=Thread.currentThread().getId();
        log.info("线程id为:{}",id);
        userService.updateById(user);
        return R.success("用户信息修改成功");
    }
}
