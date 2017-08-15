package com.efun.game.user.dao;

import com.efun.game.user.entity.UserAttr;

public interface UserAttrDao {
    int deleteByPrimaryKey(Long uid);

    int insert(UserAttr record);

    int insertSelective(UserAttr record);

    UserAttr selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserAttr record);

    int updateByPrimaryKey(UserAttr record);
}