package com.efun.game.io.remoting.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.transport.AbstractChannelHandlerDelegate;
import com.efun.game.protobuf.api.CodeTypeProto.CodeType;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.protobuf.api.EncryptionTypeProto.EncryptionType;
import com.efun.game.protobuf.api.HeaderProto;
import com.efun.game.protobuf.api.MessageProto;

public class HeartbeatHandler extends AbstractChannelHandlerDelegate {
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);

    public static String KEY_READ_TIMESTAMP = "READ_TIMESTAMP";

    public static String KEY_WRITE_TIMESTAMP = "WRITE_TIMESTAMP";

    public HeartbeatHandler(ChannelHandler handler) {
        super(handler);
    }

    public void connected(Channel channel) throws RemotingException {
        setReadTimestamp(channel);
        setWriteTimestamp(channel);
        handler.connected(channel);
    }

    public void disconnected(Channel channel) throws RemotingException {
        clearReadTimestamp(channel);
        clearWriteTimestamp(channel);
        handler.disconnected(channel);
    }

    public void sent(Channel channel, Object message) throws RemotingException {
        setWriteTimestamp(channel);
        handler.sent(channel, message);
    }

    public void received(Channel channel, Object message) throws RemotingException {
        setReadTimestamp(channel);
        if (isHeartbeatRequest(message)) {
        	MessageProto.Message response = buildHeartBeatResponse(message);
            channel.send(response);
            if (logger.isInfoEnabled()) {
                int heartbeat = 60000;
                if(logger.isDebugEnabled()) {
                    logger.debug("Received heartbeat from remote channel " + channel.getRemoteAddress()
                                    + ", cause: The channel has no data-transmission exceeds a heartbeat period"
                                    + (heartbeat > 0 ? ": " + heartbeat + "ms" : ""));
                }
            }
            return;
        }
        if (isHeartbeatResponse(message)) {
            if (logger.isDebugEnabled()) {
            	logger.debug(
                    new StringBuilder(32)
                        .append("Receive heartbeat response in thread ")
                        .append(Thread.currentThread().getName())
                        .toString());
            }
            return;
        }
        handler.received(channel, message);
    }

    private void setReadTimestamp(Channel channel) {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
    }

    private void setWriteTimestamp(Channel channel) {
        channel.setAttribute(KEY_WRITE_TIMESTAMP, System.currentTimeMillis());
    }

    private void clearReadTimestamp(Channel channel) {
        channel.removeAttribute(KEY_READ_TIMESTAMP);
    }

    private void clearWriteTimestamp(Channel channel) {
        channel.removeAttribute(KEY_WRITE_TIMESTAMP);
    }

    private boolean isHeartbeatRequest(Object message) {
    	MessageProto.Message msg = (MessageProto.Message)message;
    	HeaderProto.Header header = msg.getHeader();
    	return header.getCommandId() == CommandId.HEART_BEAT_REQUEST;
    }

    private boolean isHeartbeatResponse(Object message) {
    	MessageProto.Message msg = (MessageProto.Message)message;
    	HeaderProto.Header header = msg.getHeader();
    	return header.getCommandId() == CommandId.HEART_BEAT_RESPONSE;
    }
    
    private MessageProto.Message buildHeartBeatResponse(Object message) {
    	MessageProto.Message request = (MessageProto.Message)message;
    	HeaderProto.Header requestHeader = request.getHeader();

        MessageProto.Message.Builder response = MessageProto.Message.newBuilder();
        HeaderProto.Header.Builder responseHeader = HeaderProto.Header.newBuilder();
        responseHeader.setCrcCode(requestHeader.getCrcCode());
        responseHeader.setId(requestHeader.getId());
        responseHeader.setCommandId(CommandId.HEART_BEAT_RESPONSE);
        responseHeader.setSessionId(0);
        responseHeader.setEncryptionType(EncryptionType.NONE);
        responseHeader.setCodeType(CodeType.PROTOBUF);
        response.setHeader(responseHeader);
        return response.build();
    }
    
}
