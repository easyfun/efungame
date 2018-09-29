package com.efun.wallet.dao;

import com.efun.wallet.entity.AssetRecharge;

public interface AssetRechargeDAO {
    int deleteByPrimaryKey(String id);

    int insert(AssetRecharge record);

    int insertSelective(AssetRecharge record);

    AssetRecharge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AssetRecharge record);

    int updateByPrimaryKey(AssetRecharge record);
}