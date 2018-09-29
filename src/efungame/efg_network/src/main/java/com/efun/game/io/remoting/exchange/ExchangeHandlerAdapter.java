package com.efun.game.io.remoting.exchange;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.transport.ChannelHandlerAdapter;
import com.efun.game.protobuf.api.MessageProto;
import com.google.protobuf.ByteString;

public abstract class ExchangeHandlerAdapter extends ChannelHandlerAdapter implements ExchangeHandler {
	
	public ByteString reply(Channel channel, MessageProto.Message message) throws RemotingException {
		return null;
	}

}
