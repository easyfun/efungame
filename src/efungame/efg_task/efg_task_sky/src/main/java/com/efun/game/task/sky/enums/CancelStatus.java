package com.efun.game.task.sky.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

/**
 * Created by Administrator on 2018/3/27.
 */
public enum CancelStatus implements IntegerValuedEnum{
    ACCEPTED(1),
    SUCCESSFUL(2),
    FAILED(3),
    ;

    private int value;

    private CancelStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
