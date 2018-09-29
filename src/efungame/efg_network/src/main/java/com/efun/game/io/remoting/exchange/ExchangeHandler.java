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
package com.efun.game.io.remoting.exchange;

import com.efun.game.io.remoting.Channel;
import com.efun.game.io.remoting.ChannelHandler;
import com.efun.game.io.remoting.RemotingException;
import com.efun.game.protobuf.api.MessageProto;
import com.google.protobuf.ByteString;

/**
 * ExchangeHandler. (API, Prototype, ThreadSafe)
 * 
 * @author william.liangf
 */
public interface ExchangeHandler extends ChannelHandler {

    /**
     * reply.
     * 
     * @param channel
     * @param message
     * @return response
     * @throws RemotingException
     */
    ByteString reply(Channel channel, MessageProto.Message message) throws RemotingException;
    
    void start() throws RemotingException;

}