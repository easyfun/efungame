package com.efun.game.io.remoting.handler;

import java.util.concurrent.ExecutorService;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.ExecutionException;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.io.remoting.transport.dispatcher.ChannelEventRunnable;
import com.efun.game.io.remoting.transport.dispatcher.ChannelEventRunnable.ChannelState;
import com.efun.game.io.remoting.transport.dispatcher.WrappedChannelHandler;

public class MessageOnlyChannelHandler extends WrappedChannelHandler {

	public MessageOnlyChannelHandler(ChannelHandler handler) {
		super(handler);
	}

    public void received(Channel channel, Object message) throws RemotingException {
        ExecutorService cexecutor = getExecutorService();
        try {
            cexecutor.execute(new ChannelEventRunnable(channel, handler, ChannelState.RECEIVED, message));
        } catch (Throwable t) {
            throw new ExecutionException(message, channel, getClass() + " error when process received event .", t);
        }
    }
    private ExecutorService getExecutorService() {
        ExecutorService cexecutor = executor;
        if (cexecutor == null || cexecutor.isShutdown()) { 
            cexecutor = SHARED_EXECUTOR;
        }
        return cexecutor;
    }

}
