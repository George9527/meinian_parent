package com.atguigu.dao;

import com.atguigu.pojo.Address;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;

public interface AddressDao {

    // 查询地图信息
    List<Address> findAll();

    // 添加新地址
    void addAddress(Address address);

    // 根据 ID 删除公司地址
    void deleteById(int id);

    // 查询功能
    Page<Address> selectByCondition(String queryString);
}
