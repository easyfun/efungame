package com.efun.game.task.sky.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

/**
 * Created by Administrator on 2018/3/27.
 */
public enum RetryStrategy implements IntegerValuedEnum {
    NORMAL(1),
    ;

    private int value;

    private RetryStrategy(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
