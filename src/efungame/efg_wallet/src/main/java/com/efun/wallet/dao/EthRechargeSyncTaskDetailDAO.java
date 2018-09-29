package com.efun.wallet.dao;

import com.efun.wallet.entity.EthRechargeSyncTaskDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EthRechargeSyncTaskDetailDAO {
    int deleteByPrimaryKey(Long id);

    int insert(EthRechargeSyncTaskDetail record);

    int insertList(List<EthRechargeSyncTaskDetail> records);

    int insertSelective(EthRechargeSyncTaskDetail record);

    EthRechargeSyncTaskDetail selectByPrimaryKey(Long id);

    EthRechargeSyncTaskDetail selectByBlockNumberAndTransactionIndex(@Param("blockNumber") Long blockNumber, @Param("transactionIndex") Long transactionIndex);

    int updateByPrimaryKeySelective(EthRechargeSyncTaskDetail record);

    int updateByPrimaryKey(EthRechargeSyncTaskDetail record);
}