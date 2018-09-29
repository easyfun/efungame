package com.efun.game.message.dubbo.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.efun.game.main.Handler;
import com.efun.game.protobuf.api.CommandIdProto.CommandId;
import com.efun.game.service.dubbo.MessageService;
import com.efun.game.service.dubbo.dto.DubboResultDto;

@Service
public class MessageServiceImpl implements MessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Resource
	private Map<CommandId, Handler> messageHandlerMap;

	@Override
	public DubboResultDto execute(int commandId, byte[] request) {
		DubboResultDto dto = new DubboResultDto();
		dto.setErrorCode(0);
		dto.setErrorMessage("");
		
		Handler handler = messageHandlerMap.get(CommandId.forNumber(commandId));
		if (null == handler) {
			dto.setErrorCode(-1);
			dto.setErrorMessage("系统暂不支持该请求");
		} else {
			try {
				byte[] data = handler.execute(request);
				dto.setData(data);
			} catch (Exception e) {
				logger.error("[dubbo:userService]execute error.", e);
				dto.setErrorCode(-1);
				dto.setErrorMessage(e.getMessage());
			}
		}
		return dto;
	}
}
