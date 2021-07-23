package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.TravelGroupService;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.atguigu.constant.MessageConstant.*;

@RestController
@RequestMapping("/travelgroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    // "/travelgroup/findAll.do"
    // 套装中回显 跟团游 信息
    @RequestMapping("/findAll")
    public Result findAll(){
        List<TravelGroup> lists =  travelGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS,lists);
    }



    // ========================================================================================

    // 表单提交操作
    // post "/travelgroup/add.do" this.travelItemIds,this.formData
    @RequestMapping("/add")
    public Result add(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup) {
        travelGroupService.add(travelItemIds,travelGroup);
        return new Result(true,ADD_TRAVELGROUP_SUCCESS);
    }

    // 分页处理
    // 请求地址："/travelgroup/findPage.do"
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult  = travelGroupService.findPage(queryPageBean);
        return pageResult;
    }

    // 编辑功能
    // "/travelItem/edit.do"
    @RequestMapping("/edit")
    public Result edit(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup) {
        travelGroupService.edit(travelItemIds,travelGroup);
        return new Result(true,EDIT_TRAVELGROUP_SUCCESS);
    }

    //1 发送ajax请求根据id查询跟团游信息，用于基本信息回显
    // "/travelgroup/findById.do?id="
    @RequestMapping("findById")
    public Result findById(Integer id) {
        TravelGroup travelGroup = travelGroupService.findById(id);
        return new Result(true,QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    // "/travelgroup/findTravelItemIdByTravelgroupId.do?id="
    @RequestMapping("findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> lists = travelGroupService.findTravelItemIdByTravelgroupId(id);
        return lists;
    }

}
