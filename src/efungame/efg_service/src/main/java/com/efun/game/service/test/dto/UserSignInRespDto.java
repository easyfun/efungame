package com.efun.game.service.test.dto;

import java.io.Serializable;

public class UserSignInRespDto implements Serializable {
	private static final long serialVersionUID = 2108232470463913594L;

	private long errorCode;
	private String errorMeassge;
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMeassge() {
		return errorMeassge;
	}
	public void setErrorMeassge(String errorMeassge) {
		this.errorMeassge = errorMeassge;
	}
	
	
}
