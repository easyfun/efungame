package com.efun.game.access.handlers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.access.handlers.ApplicationHandler;
import com.google.protobuf.ByteString;

public class UserAuthHandler implements ApplicationHandler {
	private static final Logger logger = LoggerFactory.getLogger(UserAuthHandler.class);

	@Override
	public ByteString reply(Object request) {
		logger.debug("[user-auth-handler]接受到请求:{}", request);
		return null;
	}
}
