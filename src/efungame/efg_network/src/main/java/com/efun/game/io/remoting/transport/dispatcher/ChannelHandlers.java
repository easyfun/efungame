/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.efun.game.io.remoting.transport.dispatcher;

import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.exchange.ExchangeHandler;
import com.efun.game.io.remoting.handler.SignInCheckHandler;
import com.efun.game.io.remoting.handler.HeaderExchangeHandler;
import com.efun.game.io.remoting.handler.HeartbeatHandler;
import com.efun.game.io.remoting.handler.MessageOnlyChannelHandler;

/**
 * @author chao.liuc
 *
 */
public class ChannelHandlers {

	public static ChannelHandler wrap(ExchangeHandler handler) {
		return ChannelHandlers.getInstance().wrapInternal(handler);
	}

	protected ChannelHandlers() {
	}

	protected ChannelHandler wrapInternal(ExchangeHandler handler) {
		ChannelHandler channelHandler = new HeaderExchangeHandler(handler);
//		channelHandler = new DecodeHandler(channelHandler);
		channelHandler = new MessageOnlyChannelHandler(channelHandler);
		channelHandler = new HeartbeatHandler(channelHandler);
		channelHandler = new SignInCheckHandler(channelHandler);
//		channelHandler = new MultiMessageHandler(channelHandler);
		return channelHandler;
	}

	private static ChannelHandlers INSTANCE = new ChannelHandlers();

	protected static ChannelHandlers getInstance() {
		return INSTANCE;
	}

	static void setTestingChannelHandlers(ChannelHandlers instance) {
		INSTANCE = instance;
	}
}