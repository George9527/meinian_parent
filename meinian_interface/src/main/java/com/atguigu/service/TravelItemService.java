package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);


    void deleteById(Integer id);

    List<TravelItem> findAll();

}

