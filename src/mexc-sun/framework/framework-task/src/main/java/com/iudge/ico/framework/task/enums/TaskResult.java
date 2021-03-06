package com.iudge.ico.framework.task.enums;

import com.iudge.ico.framework.task.mybatis.IntegerValuedEnum;

public enum TaskResult implements IntegerValuedEnum {
    SUCCESSFUL(1),
    FAIL(2),
    RETRY(3),
    ;

    private int value;

    private TaskResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return 0;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
