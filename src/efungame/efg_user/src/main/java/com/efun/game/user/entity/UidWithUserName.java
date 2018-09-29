package com.efun.game.user.entity;

import java.util.Date;

import com.efun.game.common.enums.UserEnums.UsedStatus;

public class UidWithUserName {
    private Long uid;

    private String userName;

    private UsedStatus usedStatus;
    
    private Date updateTime;

    private Date createTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public UsedStatus getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(UsedStatus usedStatus) {
        this.usedStatus = usedStatus;
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