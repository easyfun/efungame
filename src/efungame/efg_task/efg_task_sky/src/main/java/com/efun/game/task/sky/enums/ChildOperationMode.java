package com.efun.game.task.sky.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

/**
 * Created by Administrator on 2018/3/27.
 */
public enum ChildOperationMode implements IntegerValuedEnum {
    SKIP(1),
    CANCEL(2),
    ;

    private int value;

    private ChildOperationMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
