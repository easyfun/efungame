package com.efun.wallet.thread.eth;

import com.efun.wallet.service.eth.EthBlockNumberDispatchInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/3/30.
 * 获取BTC，ETH最新区块，插入区块同步记录
 */
//@Component
public class EthBlockDispatchRunner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EthBlockDispatchRunner.class);

    @Autowired
    private EthBlockNumberDispatchInterface ethBlockNumberDispatchInterface;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ethBlockNumberDispatchInterface.run();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("充值分发任务执行出错.", e);
            }

            // 1分钟同步一次
            for (int i=0; i<1*60*100; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
