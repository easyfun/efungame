package com.efun.game.web.test.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.efun.game.common.utils.HttpUtil;

public class HttpUtilTest {

	@Test
	public void post() throws Exception {
		System.out.println("begin test");
		String url = "http://localhost:8080/efg_web_test/hello/world";
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", "hello world");
		String paramsStr = "param="+JSON.toJSONString(params);
		
		String charset = "utf-8";
		String response = HttpUtil.postRequest(url, paramsStr, charset);
		
		System.out.println("response="+response);
	}
}
