package com.mexc.sun.core.member.service.impl;

import com.mexc.sun.core.member.dao.MemberDAO;
import com.mexc.sun.core.member.entity.Member;
import com.mexc.sun.core.member.service.CoreMemberIndependentTransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by easyfun on 2018/4/24.
 */
@Service
public class CoreMemberIndependentTransactionServiceImpl implements CoreMemberIndependentTransactionService {
    @Resource
    private MemberDAO memberDAO;

    @Override
    public int insertMember(Member member) {
        return memberDAO.insert(member);
    }
}
