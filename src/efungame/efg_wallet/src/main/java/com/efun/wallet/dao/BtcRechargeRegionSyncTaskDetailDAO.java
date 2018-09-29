package com.efun.wallet.dao;

import com.efun.wallet.entity.BtcRechargeRegionSyncTaskDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BtcRechargeRegionSyncTaskDetailDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BtcRechargeRegionSyncTaskDetail record);

    int insertSelective(BtcRechargeRegionSyncTaskDetail record);

    int insertList(List<BtcRechargeRegionSyncTaskDetail> record);

    BtcRechargeRegionSyncTaskDetail selectByPrimaryKey(Long id);

    BtcRechargeRegionSyncTaskDetail selectByBatchId(Long batchId);

    BtcRechargeRegionSyncTaskDetail selectByTxId(String txid);

    BtcRechargeRegionSyncTaskDetail selectByTxIdAndCategoryAndAddressAndAccount(@Param("txid") String txid, @Param("category") String category, @Param("address")String address, @Param("account") String account);

    int updateByPrimaryKeySelective(BtcRechargeRegionSyncTaskDetail record);

    int updateByPrimaryKey(BtcRechargeRegionSyncTaskDetail record);
}