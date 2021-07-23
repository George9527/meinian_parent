package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    // 保存预约信息到预约表 --- 下单
    void add(Order order);

    // 根据 已知三个条件 查询是否存在重复订单 会员ID、时间日期、套餐ID
    List<Order> findByCondition(Order order);

    // 根据预约id查询预约相关信息
    Map findById(Integer id);

    // 今日预约数
    int getTodayOrderNumber(String today);

    // 今日已出游数
    int getTodayVisitsNumber(String today);

    // 本周预约数(>=本周的周一的日期 <=本周的周日的日期)
    int getThisWeekAndMonthOrderNumber(Map<String, Object> paramWeek);

    // 本周已出游数
    int getThisWeekAndMonthVisitsNumber(Map<String, Object> paramWeekVisit);

    // 热门套餐
    List<Map<String, Object>> findHotSetmeal();
}
