package com.atguigu.dao;

import com.atguigu.pojo.User;

public interface UserDao {

    // 根据用户名称来查询t_user数据表
    User findUserByUsername(String username);
}
