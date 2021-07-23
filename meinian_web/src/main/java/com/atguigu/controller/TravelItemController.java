package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.TravelItemService;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.atguigu.constant.MessageConstant.*;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    // 处理
    // axios.post("/travelItem/add.do",this.formData)
    // @RequestBody 表示对象和json数据进行转换
    @RequestMapping("/add")
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.add(travelItem);
            return new Result(true,ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,ADD_TRAVELITEM_FAIL);
        }
    }

    // 分页处理
    // /travelItem/findPage.do
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        // 从数据中获取当前分页信息
        return travelItemService.findPage(queryPageBean);
    }

    // 接收ID
    // /travelItem/findById
    @RequestMapping("/findById")
    public Result findById(Integer id) {
       TravelItem travelItem = travelItemService.findById(id);
       return new Result(true,DELETE_TRAVELITEM_SUCCESS,travelItem);
    }

    // 编辑表单处理
    // "/travelItem/edit.do"
    // 处理从前端获取json数据 --- 需要@RequestBody 进行转换
    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelItem travelItem) {
        travelItemService.edit(travelItem);
        return new Result(true,EDIT_TRAVELITEM_SUCCESS);
    }

    // 删除表单功能
    // "/travelItem/delete"
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            travelItemService.deleteById(id);
            return new Result(true,DELETE_TRAVELITEM_SUCCESS);
        } catch (RuntimeException e) {
            // 运行时异常，表示自由行和跟团游的关联表中存在数据
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,DELETE_TRAVELITEM_FAIL);
        }
    }

    // 在跟团游中显示自由行数据
    // axios.get("/travelItem/findAll.do")
    @RequestMapping("findAll")
    public Result findAll(){
        List<TravelItem> lists =  travelItemService.findAll();
        return new Result(true, QUERY_TRAVELITEM_SUCCESS,lists);
    }
}
