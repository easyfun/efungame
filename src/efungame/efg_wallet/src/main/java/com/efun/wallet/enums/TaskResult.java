package com.efun.wallet.enums;

import com.efun.game.common.mybatis.StringValuedEnum;

/**
 * Created by Administrator on 2018/3/30.
 */
public enum TaskResult implements StringValuedEnum {
    ACCEPTED("ACCEPTED"),
    RETRIED("RETRIED"),
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED")
    ;

    private String value;

    private TaskResult(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
