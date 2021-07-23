package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    // 弹出表单中的添加功能
    void add(Setmeal setmeal);

    // 中间表
    void setSetmealAndTravelgroup(Map map);

    // 分页功能
    Page<Setmeal> findPage(String queryString);

    // 获取所有旅游套餐
    List<Setmeal> getSetmeal();

    // 根据查询到的项目ID 获取项目信息 显示在预约详情
    Setmeal findById(Integer id);

    // 查询套餐的数量
    List<Map<String, Object>> findSetmealCount();
}
