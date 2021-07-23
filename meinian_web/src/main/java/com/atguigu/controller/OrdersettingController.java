package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.OrdersettingService;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    // 编辑预约人数
    // "/ordersetting/editNumberByDate.do"
    @RequestMapping("editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            ordersettingService.editNumberByDate(orderSetting);

            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ORDERSETTING_FAIL);
        }
    }


    // ===============================================================================================

    // 查询 数据库 回显预约信息
    // "/ordersetting/getOrderSettingByMonth.do?date"
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
        /**  传递的参数
         *   date（格式：2019-8）
         构建的数据List<Map>
         map.put(“date”,1);
         map.put(“number”,120);
         map.put(“reservations”,10);

           查询方案：SELECT * FROM t_ordersetting WHERE orderDate LIKE '2019-08-%'
           查询方案：SELECT * FROM t_ordersetting WHERE orderDate BETWEEN '2019-9-1' AND '2019-9-31'
         */
        try {
            List<Map> list = ordersettingService.getOrderSettingByMonth(date);
            return new  Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new  Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    // ===============================================================================================

    // 上传 Excel 文件
    // "/ordersetting/upload.do" name="excelFile"
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            // 用工具类方法读取 Excel文件
            List<String[]> list = POIUtils.readExcel(excelFile);
            // 新建一个list集合存放 data
            List<OrderSetting> orderSettings = new ArrayList<>();
            // 遍历文件内容 --- 取出 list中的 数据
            for (String[] str : list) {
                OrderSetting orderSetting = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));
                orderSettings.add(orderSetting);
            }
            // 调用业务进行保存
            ordersettingService.add(orderSettings);
            // 返回一个结果类 不带返回值
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
