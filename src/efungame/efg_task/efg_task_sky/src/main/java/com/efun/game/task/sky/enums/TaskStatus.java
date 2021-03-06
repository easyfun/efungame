package com.efun.game.task.sky.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

/**
 * Created by Administrator on 2018/3/27.
 */
public enum TaskStatus implements IntegerValuedEnum {
    ACCEPTED(1),
    RETRYING(2),
    PAUSED(3),
    CANCELLED(4),
    SUCCESSFUL(5),
    FAILED(6),
    ;

    private TaskStatus(int value) {
        this.value = value;
    }

    private int value;

    @Override
    public int getValue() {
        return value;
    }
}
