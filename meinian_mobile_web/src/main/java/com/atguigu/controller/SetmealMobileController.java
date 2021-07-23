package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    // 根据查询到的项目ID 获取项目信息
    // axios.post("/setmeal/findById.do?id="
    @RequestMapping("/findById")
    public Result findById(int id){
        try{
            // 根据 ID 获取 setmeal 对象的信息
            Setmeal setmeal = setmealService.findById(id);
            // 放回setmeal对象
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    // =====================================================================

    // 获取所有旅游套餐
    // ("/setmeal/getSetmeal.do")
    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> lists = setmealService.getSetmeal();
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,lists);
    }

}
