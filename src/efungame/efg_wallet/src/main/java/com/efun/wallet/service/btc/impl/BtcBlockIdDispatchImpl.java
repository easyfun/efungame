package com.efun.wallet.service.btc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efun.wallet.constant.RedisKey;
import com.efun.wallet.dao.BtcRechargeRegionSyncTaskDetailDAO;
import com.efun.wallet.entity.BtcRechargeRegionSyncTaskDetail;
import com.efun.wallet.enums.CoinMarketName;
import com.efun.wallet.enums.RechargeRegionSyncTaskStatus;
import com.efun.wallet.service.btc.BtcBlockIdDispatchInterface;
import com.efun.wallet.util.BtcApiUtil;
import com.efun.wallet.util.RedisData;
import com.efun.wallet.util.RedisTaskUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 * 获取BTC，ETH最新区块，插入区块同步记录
 */
@Service
public class BtcBlockIdDispatchImpl implements BtcBlockIdDispatchInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(BtcBlockIdDispatchImpl.class);

    @Autowired
    private RedisData redisData;

    @Autowired
    private RedisTaskUtil redisTaskUtil;

    @Autowired
    private BtcApiUtil btcApiUtil;

    @Resource
    private BtcRechargeRegionSyncTaskDetailDAO btcRechargeRegionSyncTaskDetailDAO;

    @Override
    public void run() {
        // 同步BTC
        btcDetailDispatch();
    }

    /**
     * 第二版优化用listtransations
     */
    private void btcDetailDispatch() {
        // 获取BTC最新区块id
        Long blockId = Long.valueOf(redisData.getBaseDataNoCached(RedisKey.REDIS_KEY_BTC_BLOCK_REGION_SYNC_LAST_BATCH_ID, "0"));

        List<BtcRechargeRegionSyncTaskDetail> regionSyncTaskDetails = new ArrayList<BtcRechargeRegionSyncTaskDetail>();
        long lastBlockId = blockId;
        long batchCount = getBtcRegionTransactionCount();
        JSONArray transactions = getBtcTransactions(batchCount, 0L);
        for (int i=0; i<transactions.size(); i++) {
            JSONObject transaction = transactions.getJSONObject(i);
            if (StringUtils.isEmpty(transaction.getString("txid"))
                    || StringUtils.isEmpty(transaction.getString("address"))) {
                continue;
            }
            BtcRechargeRegionSyncTaskDetail taskDetailFromDb = btcRechargeRegionSyncTaskDetailDAO.selectByTxIdAndCategoryAndAddressAndAccount(transaction.getString("txid"),
                    transaction.getString("category"), transaction.getString("address"), transaction.getString("account"));
            if (null != taskDetailFromDb) {
                continue;
            }

            long batchId = lastBlockId;
            taskDetailFromDb = btcRechargeRegionSyncTaskDetailDAO.selectByBatchId(batchId);
            if (null != taskDetailFromDb) {
                continue;
            }

            lastBlockId++;
            BtcRechargeRegionSyncTaskDetail detail = buildRechargeRegionSyncTaskDetail(batchId, CoinMarketName.BTC);
            detail.setTxid(transaction.getString("txid"));
            detail.setCategory(transaction.getString("category"));
            detail.setAddress(transaction.getString("address"));
            detail.setAccount(transaction.getString("account"));
            detail.setAmount(transaction.getString("amount"));
            regionSyncTaskDetails.add(detail);
        }

        if (!CollectionUtils.isEmpty(regionSyncTaskDetails)) {
            btcRechargeRegionSyncTaskDetailDAO.insertList(regionSyncTaskDetails);
            List<String> tasks = new ArrayList<>();
            for (BtcRechargeRegionSyncTaskDetail d: regionSyncTaskDetails) {
                tasks.add(String.valueOf(d.getBatchId()));
            }
            redisTaskUtil.addTask(RedisKey.REDIS_KEY_TASK_BTC_REGION_SYNC_TASK_DETAIL, tasks);
            redisData.setBaseData(RedisKey.REDIS_KEY_BTC_BLOCK_REGION_SYNC_LAST_BATCH_ID, String.valueOf(lastBlockId));
        }
    }

    private BtcRechargeRegionSyncTaskDetail buildRechargeRegionSyncTaskDetail(long batchId, CoinMarketName coinMarketName) {
        BtcRechargeRegionSyncTaskDetail detail = new BtcRechargeRegionSyncTaskDetail();
        detail.setCoinMarketName(coinMarketName.getValue());
        detail.setBatchId(batchId);
        detail.setCounts(1L);
        detail.setFroms(batchId);
        detail.setStatus(RechargeRegionSyncTaskStatus.CREATED.getValue());
//        detail.setFailReason(null);
//        detail.setTxid(null);
//        detail.setCategory(null);
//        detail.setAddress(null);
//        detail.setAccount(null);
//        detail.setAmount(null);
        Date current = new Date();
        detail.setCreateTime(current);
        detail.setUpdateTime(current);
        return detail;
    }

    private long getBtcRegionTransactionCount() {
        // 默认最近100条
        return Long.valueOf(redisData.getBaseDataNoCached(RedisKey.REDIS_KEY_BTC_REGION_SYNC_TRANSACTION_COUNT, "100"));
    }

    private JSONArray getBtcTransactions(Long count, Long skip) {
        // 查询钱包交易数
        JSONObject transactions = btcApiUtil.listTransactions("*", count, skip);
        if (null != transactions.getString("error")) {
            LOGGER.error("查询交易记录失败, count = {}, skip = {}", count, skip);
            return null;
        }
        JSONArray results = transactions.getJSONArray("result");
        return results;
    }
}
