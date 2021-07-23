package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    // 根据预约id查询预约相关信息
    // axios.post("/order/findById.do?id=" + id)
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map =   orderService.findById(id);
        return new Result(true,MessageConstant.ORDER_SUCCESS,map);
    }

    // 处理预约页面信息
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {

        // 获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        // 获取用户输入的手机号码
        String telephone = (String) map.get("telephone");
        // 获取Redis存储的真实验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        // 判断用输入的验证码是否正确
        if (codeInRedis == null || !validateCode.equals(codeInRedis)){
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 设置移动端下单
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 提交订单
        Result result =  orderService.order(map);
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS,result);
    }
}



