package com.efun.game.access.dispatcher;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.access.handlers.ApplicationHandler;
import com.efun.game.access.handlers.impl.UserAuthHandler;
import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.exchange.ExchangeHandlerAdapter;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.app.user.UserSignInReqProto.UserSignInReq;
import com.efun.game.protobuf.api.HeaderProto;
import com.efun.game.protobuf.api.MessageProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.Parser;


public class AccessExchangeHandler extends ExchangeHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AccessExchangeHandler.class);

	private Map<CommandId, Parser<?>> parsers = new HashMap<>();
	private Map<CommandId, ApplicationHandler> handlers = new HashMap<>();
	 
	public AccessExchangeHandler() {
	}
	 
	public void start() throws RemotingException {
		loadParsers();
		loadHandlers();
	}
	
	@Override
	public ByteString reply(Channel channel, MessageProto.Message message) throws RemotingException {
		// command_id -> handler
		// command_id -> protobuf request
		HeaderProto.Header header = message.getHeader();
		
		Parser<?> parser = parsers.get(header.getCommandId());
		ApplicationHandler handler = handlers.get(header.getCommandId());
		
		try {
			return handler.reply(parser.parseFrom(message.getBody()));
		} catch (Exception e) {
			logger.error("业务处理失败.", e);
			throw new RemotingException(channel, e.getMessage());
		}
	}
	
	private void loadParsers() {
		 parsers.put(CommandId.USER_SIGN_IN_REQUEST, UserSignInReq.parser());

	}
	
	private void loadHandlers() {
		 handlers.put(CommandId.USER_SIGN_IN_REQUEST, new UserAuthHandler());
		
	}
}
