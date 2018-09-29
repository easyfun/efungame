package com.efun.game.io.remoting.handler;

import com.efun.game.io.remoting.ChannelHandler;

public interface ChannelHandlerDelegate extends ChannelHandler {
    public ChannelHandler getHandler();
}
