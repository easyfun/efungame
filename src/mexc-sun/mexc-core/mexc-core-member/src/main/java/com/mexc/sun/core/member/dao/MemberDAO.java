package com.mexc.sun.core.member.dao;

import com.mexc.sun.core.member.entity.Member;

public interface MemberDAO {
    int deleteByPrimaryKey(Long memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long memberId);

    Member selectByEmail(String email);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}