package com.efun.wallet.util;

/**
 * Created by Administrator on 2018/3/30.
 */
public class EthApiUtilTest {
    public static void main(String[] args) {
        EthApiUtil util = new EthApiUtil();
        System.out.println(util.eth_blockNumber());
    }
}
