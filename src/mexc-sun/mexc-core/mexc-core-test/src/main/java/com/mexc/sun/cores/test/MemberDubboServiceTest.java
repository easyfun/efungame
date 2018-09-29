package com.mexc.sun.cores.test;

import com.alibaba.fastjson.JSON;
import com.mexc.sun.framework.common.dto.base.BaseResultDTO;
import com.mexc.sun.framework.test.SpringTestCase;
import com.mexc.sun.core.service.dto.MemberRegisterParamDTO;
import com.mexc.sun.core.service.dubbo.MemberDubboService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by easyfun on 2018/4/23.
 */
public class MemberDubboServiceTest extends SpringTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberDubboServiceTest.class);

    @Autowired
    private MemberDubboService memberService;

    @Test
    public void register() {
        MemberRegisterParamDTO paramDTO = new MemberRegisterParamDTO();
        paramDTO.setEmail("1060887552@qq.com");
        LOGGER.debug("paramDTO={}", JSON.toJSONString(paramDTO));

        BaseResultDTO resultDTO = memberService.register(paramDTO);
        LOGGER.debug("resultDTO={}", JSON.toJSONString(resultDTO));
    }
}
