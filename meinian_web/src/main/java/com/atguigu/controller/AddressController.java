package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    // 查询地图信息
    // GET "/address/findAllMaps.do" 无参需返回集合map
    // var adds = []; List 集合
    @RequestMapping("findAllMaps")
    public Map findAllMaps() {
       List<Address> addressList = addressService.findAll();
       // 需要取出集合中的坐标与坐标名称
        // 注意返回值 Map的数据结构

//        for(var x=0;x<data.gridnMaps.length;x++){
//            adds.push(new BMap.Point(data.gridnMaps[x].lng,data.gridnMaps[x].lat));
//            addNames.push(data.nameMaps[x].addressName);
//        }
        // 创建数组，存放经纬度
        List<Map> gridnMaps = new ArrayList<>();
        // 创建数组，存放公司名称
        List<Map> nameMaps = new ArrayList<>();

        for (Address address : addressList) {

            // 遍历集合 取出数据 存放在 经纬度map中
            Map gridnMap = new HashMap<>();
            // 注意 这里的 lng 与 lat 已经在前端定义好
            gridnMap.put("lng",address.getLng());
            gridnMap.put("lat",address.getLat());
            // 将取出的数据封装回 gridnMaps中
            gridnMaps.add(gridnMap);

            // 遍历集合 取出数据 存放在 地址名称 map 中
            Map nameMap = new HashMap<>();
            // 注意 这里的 addressName 已经在前端定义好
            nameMap.put("addressName",address.getAddressName());
            // 将取出的数据封装回 addressNames中
            nameMaps.add(nameMap);

            // 将两个map重新封装回 map

        }
        Map map = new HashMap<>();
        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);
        return map;
    }

    // 分页处理
    // 请求地址：axios.post("/address/findPage.do",param)
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=null;
        try{
            pageResult= addressService.findPage(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageResult;
    }

    // 添加新地址
    // axios.post("/address/addAddress.do",param)
    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return new Result(true,"地址保存成功");
    }

    // 根据 ID 删除公司地址
    // axios.post("/address/deleteById.do?id="+row.id)
    @RequestMapping("deleteById")
    public Result deleteById(int id) {
        try {
            addressService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_Address_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_Address_FAIL);
        }
    }

}
