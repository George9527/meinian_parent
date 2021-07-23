package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {

    void add(TravelGroup travelGroup);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup);

    void deleteTravelgroupAndTravelitem(Integer id);

    void setTravelgroupAndTravelitem(Map map);

    List<TravelGroup> findAll();

    // 在 TravelGroup 中 根据ID查询TravelGroupLise集合
    List<TravelGroup> findTravelGroupListById(Integer id);

}
