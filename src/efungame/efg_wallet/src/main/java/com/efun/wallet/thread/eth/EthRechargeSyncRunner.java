package com.efun.wallet.thread.eth;

import com.efun.wallet.constant.RedisKey;
import com.efun.wallet.enums.TaskResult;
import com.efun.wallet.service.eth.EthRechargeSyncInteface;
import com.efun.wallet.util.RedisTaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/3/30.
 */
public class EthRechargeSyncRunner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EthRechargeSyncRunner.class);

    @Autowired
    private EthRechargeSyncInteface ethRechargeSyncInteface;

    @Autowired
    private RedisTaskUtil redisTaskUtil;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            doJob();

            // 10秒钟同步一次
            for (int i=0; i<10*1*20; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }

    private void doJob() {
        try {
            String taskParam = redisTaskUtil.popTask(RedisKey.REDIS_KEY_ETH_BLOCK_SYNC_TASK);
            TaskResult taskResult = ethRechargeSyncInteface.run(taskParam);
            if (taskResult == TaskResult.RETRIED) {
                redisTaskUtil.addTask(RedisKey.REDIS_KEY_ETH_BLOCK_SYNC_TASK, taskParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("eth类充值分发任务执行出错.", e);
        }
    }
}
