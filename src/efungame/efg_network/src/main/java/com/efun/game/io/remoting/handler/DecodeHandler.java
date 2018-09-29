package com.efun.game.io.remoting.handler;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.transport.AbstractChannelHandlerDelegate;

public class DecodeHandler extends AbstractChannelHandlerDelegate {

	public DecodeHandler(ChannelHandler handler) {
		super(handler);
	}

	@Override
	public void received(Channel channel, Object message) throws RemotingException {
		
		handler.received(channel, message);
	}
}
