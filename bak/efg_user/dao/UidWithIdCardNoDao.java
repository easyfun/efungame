package com.efun.game.user.dao;

import com.efun.game.user.entity.UidWithIdCardNo;

public interface UidWithIdCardNoDao {
    int deleteByPrimaryKey(Long uid);

    int insert(UidWithIdCardNo record);

    int insertSelective(UidWithIdCardNo record);

    UidWithIdCardNo selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UidWithIdCardNo record);

    int updateByPrimaryKey(UidWithIdCardNo record);
}