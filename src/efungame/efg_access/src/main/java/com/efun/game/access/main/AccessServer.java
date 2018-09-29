package com.efun.game.access.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.access.dispatcher.AccessExchangeHandler;
import com.efun.game.io.netty.server.NettyServer;

public class AccessServer {
	private static final Logger logger=LoggerFactory.getLogger(AccessServer.class);
	
	public static void main(String[] args) {
		logger.info("access server start");
		AccessExchangeHandler requestHandler = new AccessExchangeHandler();
		NettyServer server = new NettyServer(7708);
		server.setExchangeHandler(requestHandler);
		
		try {
			server.start();
		} catch (Exception e) {
			logger.error("server start fail.");
			logger.error("access server catch exception.", e);
		}
		logger.info("access server stop");
	}
}
