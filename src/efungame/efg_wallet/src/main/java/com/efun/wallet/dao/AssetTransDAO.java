package com.efun.wallet.dao;

import com.efun.wallet.entity.AssetTrans;

public interface AssetTransDAO {
    int deleteByPrimaryKey(String id);

    int insert(AssetTrans record);

    int insertSelective(AssetTrans record);

    AssetTrans selectByPrimaryKey(String id);

    int countByTransNo(String transNo);

    int updateByPrimaryKeySelective(AssetTrans record);

    int updateByPrimaryKey(AssetTrans record);
}