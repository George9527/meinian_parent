package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

public interface OrderService {

    Result order(Map map);

    // 根据预约id查询预约相关信息
    Map findById(Integer id);
}
