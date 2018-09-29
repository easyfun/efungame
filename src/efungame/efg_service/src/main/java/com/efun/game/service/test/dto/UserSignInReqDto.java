package com.efun.game.service.test.dto;

import java.io.Serializable;

public class UserSignInReqDto implements Serializable {

	private static final long serialVersionUID = 1431190133110345548L;

    private int userType;
    private String userName;
    private String password;
    private int appType;
    private String loginIp;
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAppType() {
		return appType;
	}
	public void setAppType(int appType) {
		this.appType = appType;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
    
    

}
