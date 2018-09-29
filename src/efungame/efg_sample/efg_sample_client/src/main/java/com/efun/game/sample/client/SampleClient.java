package com.efun.game.sample.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.netty.client.NettyClient;

public class SampleClient {
	private static final Logger logger = LoggerFactory.getLogger(SampleClient.class);
	
	public static void main(String[] args) {
		logger.debug("sample client start");
		try {
			NettyClient client = new NettyClient(new SampleClientHandler());
			client.connect("127.0.0.1", 7708);
		} catch (Exception e) {
			logger.error("error.", e);
		}
		logger.debug("sample client finish");
	}
}


