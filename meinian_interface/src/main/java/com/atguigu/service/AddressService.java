package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Address;

import java.util.List;

public interface AddressService {

    // 查询地图信息
    List<Address> findAll();

    // 分页功能
    PageResult findPage(QueryPageBean queryPageBean);

    // 添加新地址
    void addAddress(Address address);

    // 根据 ID 删除公司地址
    void deleteById(int id);
}
