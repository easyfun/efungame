package com.efun.game.io.netty.server.handler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efun.game.common.utils.NetUtils;
import com.efun.game.io.netty.channel.NettyChannel;
import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyHandler extends ChannelInboundHandlerAdapter {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NettyHandler.class);

	private final Map<String, Channel> channels = new ConcurrentHashMap<>();

	private final ChannelHandler handler;

	public NettyHandler(ChannelHandler handler) {
		if (null == handler) {
			throw new IllegalArgumentException("null == handler");
		}
		this.handler = handler;
	}

	public Map<String, Channel> getChannels() {
		return channels;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), handler);
		try {
			if (null != channel) {
				channels.put(NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()), channel);
			}
			handler.connected(channel);
		} finally {
			NettyChannel.removeChannelIfDisconnected(ctx.channel());
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), handler);
		try {
			if (null != channel) {
				channels.remove(NetUtils.toAddressString((InetSocketAddress)ctx.channel().remoteAddress()));
				handler.disconnected(channel);
			}
		} finally {
			NettyChannel.removeChannelIfDisconnected(ctx.channel());
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), handler);
        try {
            handler.received(channel, msg);
        } finally {
            NettyChannel.removeChannelIfDisconnected(ctx.channel());
        }
	}

//	@Override
//	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//		super.channelWritabilityChanged(ctx);
//        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), handler);
//        try {
//            handler.sent(channel, null);
//        } finally {
//            NettyChannel.removeChannelIfDisconnected(ctx.channel());
//        }
//	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel(), handler);
        try {
            handler.caught(channel, cause);
        } finally {
            NettyChannel.removeChannelIfDisconnected(ctx.channel());
        }
	}
}
