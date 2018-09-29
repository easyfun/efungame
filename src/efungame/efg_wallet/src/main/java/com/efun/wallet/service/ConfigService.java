package com.efun.wallet.service;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface ConfigService {
    int getSysRechargeBlockByVcoinId(String vcoinId) throws RuntimeException;
}
