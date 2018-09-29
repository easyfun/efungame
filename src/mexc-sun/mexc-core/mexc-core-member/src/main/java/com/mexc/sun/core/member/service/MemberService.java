package com.mexc.sun.core.member.service;

import com.mexc.sun.core.member.entity.Member;

/**
 * Created by easyfun on 2018/4/24.
 */
public interface MemberService {
    Member queryMemberByEmail(String email);
    int createMember(Member member);
}
