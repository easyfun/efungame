package com.efun.game.user.entity;

import java.util.Date;

import com.efun.game.common.enums.UserEnums.Gender;
import com.efun.game.common.enums.UserEnums.MarriageStatus;
import com.efun.game.common.enums.UserEnums.SecurityLevel;
import com.efun.game.common.enums.UserEnums.SignUpAppType;

public class UserDetail {
    private Long uid;

    private String nickName;

    private String headPictureUrl;

    private Gender gender;

    private String realName;

    private Date birthday;

    private Integer age;

    private MarriageStatus marriageStatus;

    private String education;

    private SecurityLevel securityLevel;

    private String cityCode;

    private String proCode;

    private String signUpIp;

    private String signUpChannel;

    private SignUpAppType signUpAppType;

    private Date signUpDate;

    private Date updateTime;

    private Date createTime;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPictureUrl() {
		return headPictureUrl;
	}

	public void setHeadPictureUrl(String headPictureUrl) {
		this.headPictureUrl = headPictureUrl;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public MarriageStatus getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(MarriageStatus marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getSignUpIp() {
		return signUpIp;
	}

	public void setSignUpIp(String signUpIp) {
		this.signUpIp = signUpIp;
	}

	public String getSignUpChannel() {
		return signUpChannel;
	}

	public void setSignUpChannel(String signUpChannel) {
		this.signUpChannel = signUpChannel;
	}

	public SignUpAppType getSignUpAppType() {
		return signUpAppType;
	}

	public void setSignUpAppType(SignUpAppType signUpAppType) {
		this.signUpAppType = signUpAppType;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
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