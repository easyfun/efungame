package com.efun.wallet.service.eth;

import com.efun.wallet.enums.TaskResult;

/**
 * Created by Administrator on 2018/3/31.
 */
public interface EthRechargeSyncInteface {
    TaskResult run(String taskParam);
}
