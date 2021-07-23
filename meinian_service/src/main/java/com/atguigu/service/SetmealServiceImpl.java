package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    // 弹出表单中的添加功能   ========================================================================
    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);
        // 往中间表插入数据
        setSetmealAndTravelgroup(setmeal.getId(),travelgroupIds);
    }

    // 分页功能  ===================================================================================
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 分页插件代码必须写在第一行
        // 1：初始化分页操作
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 2：使用sql语句进行查询（不必在使用mysql的limit了）
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        // 3：封装
        return  new PageResult(page.getTotal(), page.getResult());
    }


    // 获取所有旅游套餐 --- 自由行套餐
    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();

    }

    // 根据查询到的项目ID 获取项目信息 显示在预约详情
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    // 查询套餐的数量
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    // 往中间表插入数据
    // 看中间表确定 setmeal_id,travelgroup_id 的顺序
    private void setSetmealAndTravelgroup(Integer id, Integer[] travelgroupIds) {
        // 遍历 map
        for (Integer travelgroupId : travelgroupIds) {
            Map map = new HashMap<>();
            map.put("travelgroupId",travelgroupId);
            map.put("setmealId",id);
            setmealDao.setSetmealAndTravelgroup(map);
        }
    }
}
