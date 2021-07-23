package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AddressDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Address;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao addressDao;

    // 查询地图信息
    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    // 分页功能
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 实现分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Address> page =  addressDao.selectByCondition(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    // 添加新地址
    @Override
    public void addAddress(Address address) {
        addressDao.addAddress(address);
    }

    // 根据 ID 删除公司地址
    @Override
    public void deleteById(int id) {
        addressDao.deleteById(id);
    }
}
