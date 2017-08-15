package com.efun.game.user.dao;

import com.efun.game.user.entity.UidWithEmail;

public interface UidWithEmailDao {
    int deleteByPrimaryKey(Long uid);

    int insert(UidWithEmail record);

    int insertSelective(UidWithEmail record);

    UidWithEmail selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UidWithEmail record);

    int updateByPrimaryKey(UidWithEmail record);
}