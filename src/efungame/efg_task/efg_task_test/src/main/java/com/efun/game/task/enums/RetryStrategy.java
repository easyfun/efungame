package com.efun.game.task.enums;

import com.efun.game.common.mybatis.IntegerValuedEnum;

public enum RetryStrategy implements IntegerValuedEnum {
	/**
	 * 无限重试
	 */
	forever(1);

	private int code;
	
	private RetryStrategy(int code) {
		this.code = code;
	}
	
	@Override
	public int getValue() {
		return code;
	}

}
