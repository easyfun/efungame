package com.efun.game.user.dao;

import com.efun.game.user.entity.UserSignInLog;

public interface UserSignInLogDao {
    int deleteByPrimaryKey(Long uid);

    int insert(UserSignInLog record);

    int insertSelective(UserSignInLog record);

    UserSignInLog selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserSignInLog record);

    int updateByPrimaryKey(UserSignInLog record);
}