package com.efun.game.user.dao;

import com.efun.game.user.entity.UserSignInLog;

public interface UserSignInLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSignInLog record);

    int insertSelective(UserSignInLog record);

    UserSignInLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSignInLog record);

    int updateByPrimaryKey(UserSignInLog record);
}