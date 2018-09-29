package com.mexc.sun.core.service.enums;

import com.mexc.sun.framework.common.mybatis.IntegerValuedEnum;

/**
 * Created by easyfun on 2018/4/23.
 */
public enum MemberLevel implements IntegerValuedEnum {
    /** 普通用户 */
    NORMAL(1),
    /** 机构用户 */
    ORGANIZATION(2),
    /** 平台账户 */
    PLATFORM(3)
    ;

    private int value;

    private MemberLevel(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
