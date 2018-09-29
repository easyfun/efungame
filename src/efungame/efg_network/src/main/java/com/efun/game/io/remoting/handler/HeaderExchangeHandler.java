package com.efun.game.io.remoting.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.exchange.ExchangeHandler;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.api.HeaderProto;
import com.efun.game.protobuf.api.MessageProto;
import com.google.protobuf.ByteString;

public class HeaderExchangeHandler implements ChannelHandlerDelegate {
    protected static final Logger logger              = LoggerFactory.getLogger(HeaderExchangeHandler.class);

    public static String          KEY_READ_TIMESTAMP  = HeartbeatHandler.KEY_READ_TIMESTAMP;

    public static String          KEY_WRITE_TIMESTAMP = HeartbeatHandler.KEY_WRITE_TIMESTAMP;

    private final ExchangeHandler handler;

    public HeaderExchangeHandler(ExchangeHandler handler){
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        this.handler = handler;
    }

    MessageProto.Message handleRequest(Channel channel, MessageProto.Message request) throws RemotingException {
    	MessageProto.Message.Builder response = MessageProto.Message.newBuilder();
    	HeaderProto.Header.Builder header = HeaderProto.Header.newBuilder();
    	
    	HeaderProto.Header requestHeader = request.getHeader();
    	header.setCrcCode(0);
    	header.setId(requestHeader.getId());
    	header.setCommandId(CommandId.forNumber(requestHeader.getCommandId().getNumber()+1));
    	header.setSessionId(requestHeader.getSessionId());
    	header.setEncryptionType(requestHeader.getEncryptionType());
    	header.setCodeType(requestHeader.getCodeType());
    	
        // find handler by body class.
        try {
            // handle data.
            ByteString result = handler.reply(channel, request);
           	response.setBody(result);
        } catch (Throwable e) {
        	logger.error("reply error.", e);
        }
        response.setHeader(header);
        return response.build();
    }

    public void received(Channel channel, Object message) throws RemotingException {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
        try {
            MessageProto.Message response = handleRequest(channel, (MessageProto.Message)message);
            channel.send(response);
        } finally {
        }
    }

	@Override
	public void connected(Channel channel) throws RemotingException {
	}

	@Override
	public void disconnected(Channel channel) throws RemotingException {
	}

	@Override
	public void sent(Channel channel, Object message) throws RemotingException {
	}

	@Override
	public void caught(Channel channel, Throwable exception) throws RemotingException {
	}

    public ChannelHandler getHandler() {
        if (handler instanceof ChannelHandlerDelegate) {
            return ((ChannelHandlerDelegate) handler).getHandler();
        } else {
            return handler;
        }
    }

}
