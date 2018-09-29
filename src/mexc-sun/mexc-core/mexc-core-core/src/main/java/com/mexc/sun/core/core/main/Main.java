package com.mexc.sun.core.core.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by easyfun on 2018/4/23.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//        context.start();

        com.alibaba.dubbo.container.Main.main(args);

        LOGGER.debug("mexc_core stop running");
    }
}
