package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {

    void add(Integer[] travelItemIds,TravelGroup travelGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    void edit(Integer[] travelItemIds,TravelGroup travelGroup);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    List<TravelGroup> findAll();

}
