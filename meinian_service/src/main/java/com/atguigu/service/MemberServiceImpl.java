package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;

    // 根据注册手机号判断是否是会员
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    // 用户进行注册
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    // 统计每月注册人数
    @Override
    public List<Integer> findMemberCount(List<String> dataLists) {
        List<Integer> lists = new ArrayList<>();
        // 利用工具类获取每月最后一天，遍历每天的注册数
        for (String dataList : dataLists) {
            String lastDayOfMonth = DateUtils.getLastDayOfMonth(dataList);
            // 查询注册数
            Integer memberCount =  memberDao.findMemberCount(lastDayOfMonth);
            lists.add(memberCount);
        }
        return lists;
    }
}
