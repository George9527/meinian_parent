package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {

    // 弹出表单中的添加功能
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    // 分页功能
    PageResult findPage(QueryPageBean queryPageBean);

    // 获取所有旅游套餐
    List<Setmeal> getSetmeal();

    // 根据查询到的项目ID 获取项目信息 显示在预约详情
    Setmeal findById(Integer id);

    // 查询套餐的数量
    List<Map<String, Object>> findSetmealCount();
}
