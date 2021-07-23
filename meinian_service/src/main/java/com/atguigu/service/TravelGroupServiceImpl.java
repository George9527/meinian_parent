package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        // 先插入跟团表
        travelGroupDao.add(travelGroup);
        // 插入到中间表，传入两个参数
        setTravelgroupAndTravelitem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 实现分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<TravelGroup> page =  travelGroupDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {

        return travelGroupDao.findById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelgroupId(id);
    }

    // 套装中回显 跟团游 信息
    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        // 编辑跟团游表
        travelGroupDao.edit(travelGroup);
        // 先删除中间表数据
        travelGroupDao.deleteTravelgroupAndTravelitem(travelGroup.getId());
        // 在添加中间表数据
        setTravelgroupAndTravelitem(travelGroup.getId(),travelItemIds);
    }

    private void setTravelgroupAndTravelitem(Integer travelGroupId, Integer[] travelItemIds) {
        for (Integer travelItemId : travelItemIds) {
            Map map = new HashMap<>();
            map.put("travelGroupId",travelGroupId);
            map.put("travelItemIds",travelItemId);
            travelGroupDao.setTravelgroupAndTravelitem(map);
        }

    }

}
