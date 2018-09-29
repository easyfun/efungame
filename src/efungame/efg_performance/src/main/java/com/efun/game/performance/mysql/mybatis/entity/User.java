package com.efun.game.performance.mysql.mybatis.entity;

import java.util.Date;

public class User {
    private Long uid;

    private String mobile;

    private String userName;

    private String email;

    private String idCardNo;

    private Byte idCardType;

    private Byte idCardStatus;

    private String password;

    private Byte userStatus;

    private Date updateTime;

    private Date createTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public Byte getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Byte idCardType) {
        this.idCardType = idCardType;
    }

    public Byte getIdCardStatus() {
        return idCardStatus;
    }

    public void setIdCardStatus(Byte idCardStatus) {
        this.idCardStatus = idCardStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}