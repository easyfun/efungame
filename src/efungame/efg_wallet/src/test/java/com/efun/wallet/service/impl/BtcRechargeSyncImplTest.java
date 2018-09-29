package com.efun.wallet.service.impl;

import com.efun.game.commontest.SpringTestCase;
import com.efun.wallet.service.btc.BtcRechargeSyncInteface;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/4/2.
 */
public class BtcRechargeSyncImplTest extends SpringTestCase {

    @Autowired
    private BtcRechargeSyncInteface btcRechargeSyncInteface;

    @Test
    public void run() {
//        btcRechargeSyncInteface.run("5");
    }
}
