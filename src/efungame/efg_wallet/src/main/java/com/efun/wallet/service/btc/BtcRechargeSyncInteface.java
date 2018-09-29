package com.efun.wallet.service.btc;

import com.efun.wallet.enums.TaskResult;

/**
 * Created by Administrator on 2018/3/31.
 */
public interface BtcRechargeSyncInteface {
    TaskResult run(String batchId) throws RuntimeException;
}
