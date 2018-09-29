package com.mexc.sun.core.member.service.impl;

import com.mexc.sun.core.member.entity.Member;
import com.mexc.sun.core.member.dao.MemberDAO;
import com.mexc.sun.core.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by easyfun on 2018/4/24.
 */
@Service
public class MemberServiceImpl implements MemberService {
    public static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    private MemberDAO memberDAO;

    @Override
    public Member queryMemberByEmail(String email) {
        return memberDAO.selectByEmail(email);
    }

    @Override
    public int createMember(Member member) {
        return memberDAO.insert(member);
    }


}
