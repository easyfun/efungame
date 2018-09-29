package com.efun.wallet.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

/**
 * Created by Administrator on 2018/3/30.
 */
public enum RechargeRegionSyncTaskStatus implements IntegerValuedEnum {
    UNKNOWN(0),
    CREATED(1),
    CONFIRMING(2),
    SUCCESS(98),
    FAIL(99),
    ;

    private int value;

    private RechargeRegionSyncTaskStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
