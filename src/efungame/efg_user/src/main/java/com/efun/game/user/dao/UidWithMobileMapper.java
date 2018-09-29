package com.efun.game.user.dao;

import com.efun.game.user.entity.UidWithMobile;

public interface UidWithMobileMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UidWithMobile record);

    int insertSelective(UidWithMobile record);

    UidWithMobile selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UidWithMobile record);

    int updateByPrimaryKey(UidWithMobile record);
}