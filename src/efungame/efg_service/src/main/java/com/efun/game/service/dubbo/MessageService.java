package com.efun.game.service.dubbo;

import com.efun.game.service.dubbo.dto.DubboResultDto;

public interface MessageService {
	DubboResultDto execute(int commandId, byte[] request);

}
