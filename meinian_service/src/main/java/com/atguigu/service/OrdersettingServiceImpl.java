package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    // 编辑预约人数
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        // 查询数据库的预约人数
        long conut = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        // 如果设置过预约日期，更新number数量
        if(conut > 0) {
            // 判断当前的日期之前是否已经被设置过预约人数，使用当前时间作为条件查询数量
            ordersettingDao.editNumberByOrderDate(orderSetting);
        } else {
            // 如果没有设置过预约人数，执行保存
            ordersettingDao.add(orderSetting);
        }
    }


    // ===============================================================================================
    // 查询 数据库 回显预约信息
    @Override
    public List<Map> getOrderSettingByMonth(String date) {//2019-3
        // 1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = date + "-1";//2019-3-1
        String dateEnd = date + "-31";//2019-3-31
        Map<String,Object>  map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        // 2.查询当前月份的预约设置
        List<OrderSetting> list = ordersettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        // 3.将List<OrderSetting>，组织成List<Map>
//        构建的数据List<Map>
//        map.put(“date”,1);
//        map.put(“number”,120);
//        map.put(“reservations”,10);
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }



    // ===============================================================================================

    // 读取Excel文件
    @Override
    public void add(List<OrderSetting> orderSettings) {
        // 遍历OrderSetting
        for (OrderSetting orderSetting : orderSettings) {

            // 查询数据库的预约人数
            long conut = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());

            // 如果设置过预约日期，更新number数量
            if(conut > 0) {
                // 判断当前的日期之前是否已经被设置过预约人数，使用当前时间作为条件查询数量
                ordersettingDao.editNumberByOrderDate(orderSetting);
            } else {
                // 如果没有设置过预约人数，执行保存
                ordersettingDao.add(orderSetting);
            }

        }
    }


}
