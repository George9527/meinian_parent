package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.SetmealService;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    // Redis 连接池
    @Autowired
    private JedisPool jedisPool;


    // 分页功能 ====================================================================================
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        // 从数据中获取当前分页信息
        return setmealService.findPage(queryPageBean);
    }


    // 弹出表单中的添加功能  ==========================================================================
    @RequestMapping("/add")
    public Result add(Integer[] travelgroupIds, @RequestBody Setmeal setmeal) {
        setmealService.add(travelgroupIds,setmeal);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }


    // 上传图片  ===============================================================================
    // action="/setmeal/upload.do" name="imgFile"
    @RequestMapping("/upload")
    // MultipartFile --- 多媒体处理参数
    public Result upload(MultipartFile imgFile) {
        try {
            // 获取文件（图片）的名字
            String originalFilename = imgFile.getOriginalFilename();
            //  abc.jpg
            // 先找到图片当中的.
            int lastIndexOf = originalFilename.lastIndexOf(".");
            // 截取图片当中.jpg 获取后缀
            String substring = originalFilename.substring(lastIndexOf);
            // 随机生成图片的名字 UUID
            String fileName =  UUID.randomUUID().toString() + substring;
            // 上传图片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            // Redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}
