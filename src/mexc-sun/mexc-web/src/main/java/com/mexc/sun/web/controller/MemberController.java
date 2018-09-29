package com.mexc.sun.web.controller;

import com.alibaba.fastjson.JSON;
import com.mexc.sun.framework.common.dto.base.BaseResultDTO;
import com.mexc.sun.core.service.dto.MemberRegisterParamDTO;
import com.mexc.sun.core.service.dubbo.MemberDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by easyfun on 2018/4/24.
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberDubboService memberDubboService;

    @RequestMapping(value = "/register", method = POST)
    @ResponseBody
    public BaseResultDTO agreementCallBackNotify(@RequestBody MemberRegisterParamDTO paramDTO) {
        LOGGER.info("paramDTO={}", JSON.toJSONString(paramDTO));
        BaseResultDTO resultDTO = memberDubboService.register(paramDTO);
        LOGGER.info("resultDTO={}", JSON.toJSONString(resultDTO));
        return resultDTO;
    }
}