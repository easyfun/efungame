package com.efun.game.user.dao;

import com.efun.game.user.entity.UserDetail;

public interface UserDetailMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);
}