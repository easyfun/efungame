package com.efun.wallet.dao;

import com.efun.wallet.entity.EthRechargeSyncTask;

import java.util.List;

public interface EthRechargeSyncTaskDAO {
    int deleteByPrimaryKey(Long id);

    int insert(EthRechargeSyncTask record);

    int insertSelective(EthRechargeSyncTask record);

    int insertList(List<EthRechargeSyncTask> records);

    EthRechargeSyncTask selectByPrimaryKey(Long id);

    EthRechargeSyncTask selectByBlockNumber(Long blockNumber);

    int updateByPrimaryKeySelective(EthRechargeSyncTask record);

    int updateByPrimaryKey(EthRechargeSyncTask record);
}