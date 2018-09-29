package com.efun.game.service.dubbo.dto;

import java.io.Serializable;

public class DubboResultDto implements Serializable {

	private static final long serialVersionUID = 7371400840368999998L;
	
	private long errorCode;
	
	private String errorMessage;
	
	private byte[] data;

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	

}
