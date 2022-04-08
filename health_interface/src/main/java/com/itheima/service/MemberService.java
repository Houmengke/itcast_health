package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * @author 侯孟珂
 * @date 2022/4/7-14:17
 */
public interface MemberService {

    public Member findByTelephone(String telephone);

    public void add(Member member);

    public List<Integer> findMemberCountByMonth(List<String> months);
}
