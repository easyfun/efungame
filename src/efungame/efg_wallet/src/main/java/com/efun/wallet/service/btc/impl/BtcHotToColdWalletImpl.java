package com.efun.wallet.service.btc.impl;

import com.efun.wallet.service.btc.BtcHotToColdWalletInterface;
import com.efun.wallet.util.RedisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/4/3.
 */
public class BtcHotToColdWalletImpl implements BtcHotToColdWalletInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(BtcHotToColdWalletImpl.class);

    @Autowired
    private RedisData redisData;

    @Override
    public void run() {
        

    }
}
