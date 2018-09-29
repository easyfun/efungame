package com.mexc.sun.core.member.entity.builder;

import com.mexc.sun.core.member.entity.Member;

import java.util.Date;

/**
 * Created by easyfun on 2018/4/24.
 */
public class MemberBuilder {
    public static Member buildTest() {
        Member member = new Member();
        member.setMemberId(System.nanoTime());
        member.setMemberPwd(String.valueOf(System.nanoTime()));
        member.setTradePwd(member.getMemberPwd());
        member.setMemberStatus(new Byte("0"));
        member.setMemberLevel(new Byte("0"));
        member.setAuthLevel(new Byte("0"));
        member.setEmail(String.valueOf(member.getMemberId()));
        member.setEmailStatus(new Byte("0"));
        Date current = new Date();
        member.setEmailActiveTime(current);
        member.setMobile(member.getEmail());
        member.setMobileStatus(new Byte("0"));
        member.setMemberStatus(new Byte("0"));
        member.setMobileActiveTime(current);
        member.setRegisterTime(current);
        member.setCreateTime(current);
        member.setUpdateTime(current);
        return member;
    }
}
