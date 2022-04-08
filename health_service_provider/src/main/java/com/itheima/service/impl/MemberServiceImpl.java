package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/4/7-14:34
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {

        Member member = memberDao.findByTelephone(telephone);
        return member;
    }

    @Override
    public void add(Member member) {
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {

        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            month = month +".31";
            Integer count = memberDao.findMemberCountBeforeDate(month);
            memberCount.add(count);
        }

        return memberCount;
    }
}
