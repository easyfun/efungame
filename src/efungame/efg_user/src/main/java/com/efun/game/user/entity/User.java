package com.efun.game.user.entity;

import java.util.Date;

import com.efun.game.common.enums.UserEnums.IdCardStatus;
import com.efun.game.common.enums.UserEnums.IdCardType;
import com.efun.game.common.enums.UserEnums.UserStatus;

public class User {
    private Long uid;

    private String mobile;

    private String userName;

    private String email;

    private String idCardNo;

    private IdCardType idCardType;

    private IdCardStatus idCardStatus;

    private String password;

    private UserStatus userStatus;

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
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public IdCardType getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(IdCardType idCardType) {
		this.idCardType = idCardType;
	}

	public IdCardStatus getIdCardStatus() {
		return idCardStatus;
	}

	public void setIdCardStatus(IdCardStatus idCardStatus) {
		this.idCardStatus = idCardStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
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