package com.efun.wallet.service.eth.impl;

import com.efun.wallet.constant.RedisKey;
import com.efun.wallet.dao.EthRechargeSyncTaskDAO;
import com.efun.wallet.dao.EthRechargeSyncTaskDetailDAO;
import com.efun.wallet.entity.EthRechargeSyncTask;
import com.efun.wallet.entity.EthRechargeSyncTaskDetail;
import com.efun.wallet.enums.CoinMarketName;
import com.efun.wallet.enums.RechargeSyncTaskStatus;
import com.efun.wallet.service.eth.EthBlockNumberDispatchInterface;
import com.efun.wallet.util.*;
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
public class EthBlockNumberDispatchImpl implements EthBlockNumberDispatchInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(EthBlockNumberDispatchImpl.class);

    @Autowired
    private RedisData redisData;

    @Autowired
    private RedisTaskUtil redisTaskUtil;

    @Autowired
    private EthApiUtil ethApiUtil;

    @Resource
    private EthRechargeSyncTaskDAO ethRechargeSyncTaskDAO;

    @Resource
    private EthRechargeSyncTaskDetailDAO ethRechargeSyncTaskDetailDAO;

    @Override
    public void run() {
        // 同步ETH
        ethBlockNumberDispatch();
    }

    /**
     * 分发Eth block
     */
    private void ethBlockNumberDispatch() {
        // 获取ETH最新区块id
        long blockNumber = Long.valueOf(redisData.getBaseDataNoCached(RedisKey.REDIS_KEY_ETH_BLOCK_SYNC_LAST_BLOCK_NUMBER, "5407893"));

        long proBlockNumber = ethApiUtil.eth_blockNumber();
        if (-1 == proBlockNumber) {
            LOGGER.info("获取Eth最新区块编号失败");
            return;
        }

        List<EthRechargeSyncTask> ethRechargeSyncTasks = new ArrayList<EthRechargeSyncTask>();
        List<EthRechargeSyncTaskDetail> ethRechargeSyncTaskDetails = new ArrayList<EthRechargeSyncTaskDetail>();
        long lastBlockNumber = blockNumber;
        long batchCount = getBatchCount();
        for (long number = blockNumber; number <= proBlockNumber; number++) {
            EthRechargeSyncTask dbTask = ethRechargeSyncTaskDAO.selectByBlockNumber(number);
            if (null != dbTask) {
                continue;
            }

            lastBlockNumber++;

            long transactionCount = ethApiUtil.eth_getBlockTransactionCountByNumber(number);
            
            EthRechargeSyncTask task = buildRechargeSyncTask(number, transactionCount, CoinMarketName.ETH);
            ethRechargeSyncTasks.add(task);

            buildEthRechargeSyncTaskDetailList(task, ethRechargeSyncTaskDetails);

            batchCount -= 1;
            if (batchCount <= 0) {
                break;
            }
        }

        if (!CollectionUtils.isEmpty(ethRechargeSyncTasks)) {
            ethRechargeSyncTaskDAO.insertList(ethRechargeSyncTasks);
            ethRechargeSyncTaskDetailDAO.insertList(ethRechargeSyncTaskDetails);

            List<String> tasks = new ArrayList<>();
            for (EthRechargeSyncTaskDetail t : ethRechargeSyncTaskDetails) {
                tasks.add(String.valueOf(t.getBlockNumber() + ":" + t.getTransactionIndex()));
            }
            redisTaskUtil.addTask(RedisKey.REDIS_KEY_ETH_BLOCK_SYNC_TASK, tasks);
            redisData.setBaseData(RedisKey.REDIS_KEY_ETH_BLOCK_SYNC_LAST_BLOCK_NUMBER, String.valueOf(lastBlockNumber));
        }
    }

    private EthRechargeSyncTask buildRechargeSyncTask(long blockNumber, long transactionCount, CoinMarketName coinMarketName) {
        EthRechargeSyncTask task = new EthRechargeSyncTask();
        task.setBlockNumber(blockNumber);
        task.setCoinMarketName(coinMarketName.getValue());
        task.setStatus(RechargeSyncTaskStatus.CREATED.getValue());
        task.setTransactionCount(transactionCount);
        Date current = new Date();
        task.setCreateTime(current);
        task.setUpdateTime(current);
        return task;
    }

    private void buildEthRechargeSyncTaskDetailList(EthRechargeSyncTask task, List<EthRechargeSyncTaskDetail> details) {
        for (long n=0; n<task.getTransactionCount(); n++) {
            EthRechargeSyncTaskDetail detail = new EthRechargeSyncTaskDetail();
            detail.setCoinMarketName(task.getCoinMarketName());
//            detail.setCoinName(null);
            detail.setBlockNumber(task.getBlockNumber());
            detail.setTransactionIndex(n);
            detail.setStatus(RechargeSyncTaskStatus.CREATED.getValue());
//            detail.setFailReason(null);
//            detail.setTransactionHash(null);
//            detail.setFromAddress(null);
//            detail.setToAddress(null);
//            detail.setAmount(null);
            Date current = new Date();
            detail.setCreateTime(current);
            detail.setUpdateTime(current);

            details.add(detail);
        }

    }

    private long getBatchCount() {
        return Long.valueOf(redisData.getBaseDataNoCached(RedisKey.REDIS_KEY_BLOCK_SYNC_BATCH_COUNT, "50"));
    }
}
