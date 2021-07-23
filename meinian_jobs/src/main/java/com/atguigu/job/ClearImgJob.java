package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    // 定时删除 七牛云 的图片
    public void clearImg() {
        // sdiff 是 redis 的一种集合比较方法，里面传入多个集合
          Set<String> set = jedisPool.getResource().sdiff(
                RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES
          );
          // 使用 迭代器 进行遍历
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            // 需要删除的图片
            String pic = iterator.next();

            System.out.println("删除图片的名称是："+pic);
            //删除图片服务器中的图片文件
            QiniuUtils.deleteFileFromQiniu(pic);
            //删除redis中的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }
    }
}
