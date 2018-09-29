package com.efun.game.message.handler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.dubbo.common.json.JSON;
import com.efun.game.common.id.IdUtils;
import com.efun.game.main.Handler;
import com.efun.game.message.protobuf.MessageProtobufBuilder;
import com.efun.game.protobuf.app.message.UpdateUserSignInStatusReqProto.UpdateUserSignInStatusReq;
import com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp;

public class UpdateUserSignInStatusHandler implements Handler {
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateUserSignInStatusHandler.class);
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> opsForHash;

	@Override
	public byte[] execute(byte[] request) throws Exception {
		UpdateUserSignInStatusReq req = UpdateUserSignInStatusReq.parseFrom(request);
		LOGGER.debug("[message:UpdateUserSignInStatus]请求: req={}", req);
		checkDuplicateSignIn(req);
		
		long sessionId = IdUtils.getInstance().createSessionId();
		
		// hash: signin.info.uid [appType.status, status] [appType.sessionId, sessionId] 
		String key = "signin.info" + req.getUid();
		List<String> fields = new ArrayList<>();
		String fieldStatus = req.getAppType().toString() + ".status";
		fields.add(fieldStatus);
		String fieldSessionId = req.getAppType().toString() + ".sessionId";
		fields.add(fieldSessionId);
		
		List<String> infos = opsForHash.multiGet(key, fields);
		LOGGER.info("上次登录状态: {}", infos);
		
		Map<String, String> map = new HashMap<>();
		map.put(fieldStatus, req.getSignInStatus().toString());
		map.put(fieldSessionId, String.valueOf(sessionId));
		opsForHash.putAll(key, map);
		
		UpdateUserSignInStatusResp.Builder respBuild = MessageProtobufBuilder.buildUpdateUserSignInStatusResp(0, "");
		respBuild.setSessionId(sessionId);
		UpdateUserSignInStatusResp resp = respBuild.build();
		LOGGER.info("[message:UpdateUserSignInStatus]应答, resp={}", resp);
		return resp.toByteArray();
	}

	private void checkDuplicateSignIn(UpdateUserSignInStatusReq req) throws Exception {
		// 下线
	}
}
