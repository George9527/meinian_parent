package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {

    // 新增会员
    void add(Member member);

    // 根据用户手机号查找会员
    Member findByTelephone(String telephone);

    // 查询注册数
    Integer findMemberCount(String lastDayOfMonth);

    // 今天新增会员数
    int getTodayNewMember(String today);

    // 总会员数
    int getTotalMember();

    // 本周新增会员数(>=本周的周一的日期)
    int getThisWeekAndMonthNewMember(String weekMonday);
}
