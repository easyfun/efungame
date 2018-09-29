package com.efun.game.io.remoting.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.transport.AbstractChannelHandlerDelegate;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.api.MessageProto;

public class SignInCheckHandler extends AbstractChannelHandlerDelegate {
	private static final Logger logger=LoggerFactory.getLogger(SignInCheckHandler.class);
	
	public static String KEY_AUTH = "AUTH";
	
	public SignInCheckHandler(ChannelHandler handler) {
		super(handler);
	}

	@Override
	public void received(Channel channel, Object message) throws RemotingException {
		Boolean auth=(Boolean)channel.getAttribute(KEY_AUTH);
		if (null != auth && true == auth) {
			super.received(channel, message);
		} else {
			if (isAuthCheckRequest((MessageProto.Message)message)) {
				super.received(channel, message);
			} else {
				logger.error("[network]未登录，不能接受消息. remoteAddr={}, message={}", channel.getRemoteAddress(), message);
				channel.close();
			}
		}
	}
	
	private boolean isAuthCheckRequest(MessageProto.Message message) {
		return CommandId.SERVER_AUTH_REQUEST.equals(message.getHeader().getCommandId()) ||
				CommandId.USER_SIGN_IN_REQUEST.equals(message.getHeader().getCommandId());
	}
}
