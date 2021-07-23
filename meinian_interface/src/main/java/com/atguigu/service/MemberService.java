package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.List;

public interface MemberService {

    // 根据注册手机号判断是否是会员
    Member findByTelephone(String telephone);

    // 用户进行注册
    void add(Member member);

    // 统计每月注册人数
    List<Integer> findMemberCount(List<String> dataLists);

}
