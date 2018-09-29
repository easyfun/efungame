package com.efun.game.user.entity;

public class UidWithIdCardNo {
    private Long uid;

    private String idCardNo;

    private Byte idCardType;

    private Byte usedStatus;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public Byte getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Byte usedStatus) {
        this.usedStatus = usedStatus;
    }
}