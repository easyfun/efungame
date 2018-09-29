package com.efun.game.io.remoting.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.protobuf.api.MessageProto;
import com.google.protobuf.ByteString;

public class DefaultExchangeHandler extends ExchangeHandlerAdapter {
	private static final Logger logger=LoggerFactory.getLogger(DefaultExchangeHandler.class);
	
	@Override
	public ByteString reply(Channel channel, MessageProto.Message request) throws RemotingException {
		return super.reply(channel, request);
	}

	@Override
	public void start() throws RemotingException {
	}
}
