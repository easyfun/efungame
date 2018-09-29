package com.efun.game.web.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value="/world")
    @ResponseBody
	public Map<String, String> world(@RequestParam("param")String param){
		LOGGER.debug("生成电子合同回调通知开始,接受到请求, param={}", param);
		Map<String, String> response = new HashMap<String, String>();
		response.put("response", param);
		LOGGER.debug("response={}", param);
		return response;
    }
}
