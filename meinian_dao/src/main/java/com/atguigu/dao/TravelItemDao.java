package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemDao {

    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    void deleteById(Integer id);

    long findCountByTravelItemItemId(Integer id);

    List<TravelItem> findAll();

    // 在 TravelItem 中 根据ID查询TravelItem集合
    List<TravelItem> findTravelItemListById(Integer id);

}
