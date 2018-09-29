package com.efun.game.access.handlers;

import com.efun.game.io.remoting.RemotingException;
import com.google.protobuf.ByteString;

public interface ApplicationHandler {
	public ByteString reply(Object request) throws RemotingException;
}
