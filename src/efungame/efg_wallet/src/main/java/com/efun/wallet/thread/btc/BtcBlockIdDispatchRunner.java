package com.efun.wallet.thread.btc;

import com.efun.wallet.service.btc.BtcBlockIdDispatchInterface;
import com.efun.wallet.service.eth.EthBlockNumberDispatchInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/3/30.
 * 获取BTC，ETH最新区块，插入区块同步记录
 */
//@Component
public class BtcBlockIdDispatchRunner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BtcBlockIdDispatchRunner.class);

    @Autowired
    private BtcBlockIdDispatchInterface btcBlockIdDispatchInterface;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                btcBlockIdDispatchInterface.run();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("btc充值分发任务执行出错.", e);
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
