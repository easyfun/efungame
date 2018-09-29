package com.mexc.sun.core.service.dto;

import com.mexc.sun.framework.common.dto.base.BaseParamDTO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by easyfun on 2018/4/23.
 */
public class MemberRegisterParamDTO extends BaseParamDTO {
    private static final long serialVersionUID = 5613656148651888007L;

    /**
     * 会话id
     */
    @NotBlank
    private String sessionId;

    /**
     * 登录密码
     */
    @NotBlank
    private String memberPwd;

    /**
     * 交易密码
     */
    @NotBlank
    private String tradePwd;

    /**
     * 邮箱
     */
    @NotBlank
    private String email;

    /**
     * 注册验证码
     */
    @NotBlank
    private String encodeRegisterCaptchaCode;

    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 注册时间
     */
    @NotBlank
    private Date registerApplyTime;

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterApplyTime() {
        return registerApplyTime;
    }

    public void setRegisterApplyTime(Date registerApplyTime) {
        this.registerApplyTime = registerApplyTime;
    }

    public String getEncodeRegisterCaptchaCode() {
        return encodeRegisterCaptchaCode;
    }

    public void setEncodeRegisterCaptchaCode(String encodeRegisterCaptchaCode) {
        this.encodeRegisterCaptchaCode = encodeRegisterCaptchaCode;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
