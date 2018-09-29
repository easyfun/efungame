package com.mexc.sun.core.service.dubbo;

import com.mexc.sun.core.service.dto.MemberRegisterParamDTO;
import com.mexc.sun.framework.common.dto.base.BaseResultDTO;

/**
 * Created by easyfun on 2018/4/23.
 */
public interface MemberDubboService {
    BaseResultDTO register(MemberRegisterParamDTO paramDTO);
}
