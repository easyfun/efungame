package com.efun.game.servicetest.user.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.protobuf.app.EnumsProto.AppType;
import com.efun.game.protobuf.app.user.UserSignUpReqProto.UserSignUpReq;
import com.efun.game.protobuf.app.user.UserSignUpRespProto.UserSignUpResp;
import com.efun.game.service.test.dto.UserSignInReqDto;
import com.efun.game.service.test.dto.UserSignInRespDto;
import com.efun.game.service.test.dubbo.UserService;

public class UserServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	private static long totalTime = 0;
	private static int totalCount = 0;

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "META-INF/spring/spring_context.xml" });
		context.start();

		UserService userService = (UserService) context.getBean("userService"); // 获取远程服务代理
		// UserSignUpReqPoroto.UserSignUpReq.Builder reqB =
		// UserSignUpReqPoroto.UserSignUpReq.newBuilder();
		// reqB.setUserName("easyfun");
		// reqB.setPassword("easyfun");
		// reqB.setAppType(AppType.APP_ANDRIOD);
		// reqB.setRegIp("127.0.0.1");
		// UserSignUpReq req=reqB.build();
		// logger.debug("req={}", req);
		// UserSignUpResp resp = userService.signUp(req);
		// logger.debug("resp={}", resp);
		for (int sum = 0; sum < 20; sum++) {
/*			totalCount = 0;
			totalTime = 0;
			for (int i = 0; i < 20; i++) {
				testSignUp(userService, 2000);
			}
			logger.debug("totalCount={}, totalTime={} ns, {} ms", totalCount, totalTime, totalTime / 1000000.00);
			logger.debug("\n");*/
			
			totalCount = 0;
			totalTime = 0;
			for (int i = 0; i < 10; i++) {
				testExecute(userService, 1000);
			}
			logger.debug("totalCount={}, totalTime={} ns, {} ms", totalCount, totalTime, totalTime / 1000000.00);
			logger.debug("\n");
			
			totalCount = 0;
			totalTime = 0;
			for (int i=0; i<10; i++) {
				testSignInDto(userService, 1000);
			}
			logger.debug("totalCount={}, totalTime={} ns, {} ms", totalCount, totalTime, totalTime / 1000000.00);
			logger.debug("\n");
		}

	}

	private static void testSignUp(UserService userService, int testCount) {
		long startTime = System.nanoTime();
		for (int i = 0; i < testCount; i++) {
			totalCount++;
			String user = "easyfun";
			String password = "easyfun";
			String resp = userService.signUp(user, password);
			// logger.debug("resp={}", resp);
		}
		long stopTime = System.nanoTime();
		totalTime += stopTime - startTime;
		logger.debug("testCount = {}, elapsed time = {} ns, {} ms", testCount, stopTime - startTime,
				(stopTime - startTime) / 1000000.0);

	}

	private static void testExecute(UserService userService, int testCount) throws Exception {
		long startTime = System.nanoTime();
		for (int i = 0; i < testCount; i++) {
			totalCount++;
			UserSignUpReq.Builder reqB = UserSignUpReq.newBuilder();
			reqB.setUserName("easyfun");
			reqB.setPassword("easyfun");
			reqB.setAppType(AppType.APP_ANDRIOD);
			reqB.setRegIp("127.0.0.1");
			UserSignUpReq req = reqB.build();
			// logger.debug("req={}", req);
			byte[] resp = userService.execute(1, req.toByteArray());

			UserSignUpResp result = UserSignUpResp.parseFrom(resp);
		}
		// logger.debug("result={}", result);
		long stopTime = System.nanoTime();
		totalTime += stopTime - startTime;
		logger.debug("testCount = {}, elapsed time = {} ns, {} ms", testCount, stopTime - startTime,
				(stopTime - startTime) / 1000000.0);

	}
	
	private static void testSignInDto(UserService userService, int testCount) {
		long startTime = System.nanoTime();
		for (int i = 0; i < testCount; i++) {
			totalCount++;
			UserSignInReqDto reqB = new UserSignInReqDto();
			reqB.setUserName("easyfun");
			reqB.setPassword("easyfun");
			reqB.setAppType(1);
			reqB.setLoginIp("127.0.0.1");
			// logger.debug("req={}", req);
			UserSignInRespDto resp = userService.signInDto(reqB);
		}
		// logger.debug("result={}", result);
		long stopTime = System.nanoTime();
		totalTime += stopTime - startTime;
		logger.debug("testCount = {}, elapsed time = {} ns, {} ms", testCount, stopTime - startTime,
				(stopTime - startTime) / 1000000.0);
		
	}
}
