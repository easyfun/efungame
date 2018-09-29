package com.efun.game.web.test.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * Hello world!
 *
 */
public class HelloClientTest {	

	@Test
	public void world() throws InterruptedException {
		System.out.println("begin test");
		String url = "http://localhost:8080/efg_web_test/hello/world";
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", "hello world");
		String paramsStr = "param=" + JSON.toJSONString(params);
		String charset = "utf-8";
		Integer timeout = 150000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).build();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		for (int i=0; i<1000; i++) {
			if (false == postRequest(requestConfig, httpClient, url, paramsStr, charset)) {
				System.out.println("request fail");
				break;
			}
			Thread.sleep(200);
		}

		System.out.println("end test");
	}

	private boolean postRequest(RequestConfig requestConfig, CloseableHttpClient httpClient, String url, String paramsStr,
			String charset) {
		boolean request = false;
		String result = "";
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(paramsStr, charset);
			stringEntity.setContentType("application/x-www-form-urlencoded");

			HttpPost post = new HttpPost(url);
			post.setEntity(stringEntity);
			post.setConfig(requestConfig);
			response = httpClient.execute(post);

			HttpEntity resEntity = response.getEntity();
			result = EntityUtils.toString(resEntity, charset);
			System.out.println(result);

			if (response.getStatusLine().getStatusCode() == 200) {
				request = true;
				return request;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request = false;
			return request;
		} finally {
			System.out.println("finally");
			System.out.println(request);
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		request = false;
		return request;
	}

}
