package com.efun.wallet.thread.btc;

import com.efun.wallet.constant.RedisKey;
import com.efun.wallet.enums.TaskResult;
import com.efun.wallet.service.btc.BtcRechargeSyncInteface;
import com.efun.wallet.util.RedisTaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/3/30.
 */
public class BtcRechargeSyncRunner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BtcRechargeSyncRunner.class);

    @Autowired
    private BtcRechargeSyncInteface btcRechargeSyncInteface;

    @Autowired
    private RedisTaskUtil redisTaskUtil;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                doJob();
            } catch (Exception e) {
                LOGGER.error("doJob错误.", e);
            }

            // 30秒同步一次
            for (int i=0; i<1*30*100; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private void doJob() {
        long size = redisTaskUtil.getTaskCount(RedisKey.REDIS_KEY_TASK_BTC_REGION_SYNC_TASK_DETAIL);
        int i = 0;
        while (i++ < size) {
            try {
                String batchId = redisTaskUtil.popTask(RedisKey.REDIS_KEY_TASK_BTC_REGION_SYNC_TASK_DETAIL);
                if (null == batchId) {
                    LOGGER.error("获取任务失败, batchId={}", batchId);
                    continue;
                }
                TaskResult taskResult = btcRechargeSyncInteface.run(batchId);
                if (taskResult == TaskResult.RETRIED) {
                    redisTaskUtil.addTask(RedisKey.REDIS_KEY_TASK_BTC_REGION_SYNC_TASK_DETAIL, batchId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("充值分发任务执行出错.", e);
            }
        }
    }

}
