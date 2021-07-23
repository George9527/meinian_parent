package com.atguigu.service;

import com.atguigu.pojo.User;

public interface UserService {

    // 根据用户名称来查询t_user数据表
    User findUserByUsername(String username);

}
