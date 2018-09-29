package com.efun.game.service.dubbo;

import com.efun.game.service.dubbo.dto.DubboResultDto;

public interface UserService {

	DubboResultDto execute(int commandId, byte[] request);
	
}
