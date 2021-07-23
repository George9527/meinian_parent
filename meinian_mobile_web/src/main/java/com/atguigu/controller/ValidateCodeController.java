package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

//    @Reference
//    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        // 利用工具生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        System.out.println("发送阿里云短信：" + code);
        // 验证码保存(保存在Redis用于验证码校验)，设置过期时间
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,
                300,code + "");
        // 返回处理结果
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    // 发送短信
    // axios.post("/validateCode/send4Order.do?telephone="
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {

        // 验证码生成
        try {
            // 利用工具生成验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);

            // 调用工具发送验证码(目前不能调用，控制台输出即可)
            System.out.println("发送阿里云短信：" + code);

            // 验证码保存(保存在Redis用于验证码校验)，设置过期时间
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,
                    300,code + "");

            // 返回处理结果
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }
}
