package com.efun.wallet.enums;

import com.efun.game.common.mybatis.StringValuedEnum;

/**
 * Created by Administrator on 2018/4/1.
 */
public enum BitCoinTransactionCategory implements StringValuedEnum {
    RECEIVE("receive"),
    SEND("send"),
    ;

    private String value;

    private BitCoinTransactionCategory(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
