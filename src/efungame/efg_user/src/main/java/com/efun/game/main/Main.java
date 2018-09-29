package com.efun.game.main;

//import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("efg_user server start");
		
		com.alibaba.dubbo.container.Main.main(args);

//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//        context.start();
//
//        try {
//			System.in.read();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // 按任意键退出
		logger.info("efg_user server stop");
	
	
	}

}
