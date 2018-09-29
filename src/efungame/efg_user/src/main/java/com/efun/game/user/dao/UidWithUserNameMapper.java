package com.efun.game.user.dao;

import com.efun.game.user.entity.UidWithUserName;

public interface UidWithUserNameMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UidWithUserName record);

    int insertSelective(UidWithUserName record);

    UidWithUserName selectByPrimaryKey(Long uid);
    
    UidWithUserName selectByUserName(String userName);

    int updateByPrimaryKeySelective(UidWithUserName record);

    int updateByPrimaryKey(UidWithUserName record);
}