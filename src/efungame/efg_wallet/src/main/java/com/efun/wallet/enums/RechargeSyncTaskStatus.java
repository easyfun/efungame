package com.efun.wallet.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

/**
 * Created by Administrator on 2018/3/30.
 */
public enum RechargeSyncTaskStatus implements IntegerValuedEnum {
    UNKNOWN(0),
    CREATED(1),
    SUCCESS(98),
    FAIL(99),
    ;

    private int value;

    private RechargeSyncTaskStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
