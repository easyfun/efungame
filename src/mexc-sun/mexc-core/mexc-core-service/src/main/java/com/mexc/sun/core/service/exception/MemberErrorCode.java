package com.mexc.sun.core.service.exception;

import com.mexc.sun.framework.common.exception.ErrorCode;

/**
 * Created by easyfun on 2018/4/28.
 */
public enum MemberErrorCode implements ErrorCode {
    /**
     * 注册验证码失效
     */
    registerCaptchaCodeTimeout("MSBE00000001"),
    /**
     * 注册验证码错误
     */
    registerCaptchaCodeError("MSBE00000002"),
    /**
     * Email用户已经注册
     */
    emailMemberHasRegistered("MSBE00000003"),

    ;

    private String errorCode;

    private MemberErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getFailCode() {
        return errorCode;
    }
}
