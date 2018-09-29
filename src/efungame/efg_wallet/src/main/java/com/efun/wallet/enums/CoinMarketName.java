package com.efun.wallet.enums;

import com.efun.game.common.mybatis.StringValuedEnum;

/**
 * Created by Administrator on 2018/3/30.
 */
public enum CoinMarketName implements StringValuedEnum {
    BTC("BTC"),
    ETH("ETH"),
    ;

    private String value;

    private CoinMarketName(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
