package com.efun.wallet.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application.xml");
        applicationContext.start();

//        String[] runnerNames = {"btcBlockDispatchRunner","btcRechargeSyncRunner","ethBlockDispatchRunner","ethRechargeSyncRunner"};
        String[] runnerNames = {"ethBlockDispatchRunner","ethRechargeSyncRunner"};
//        String[] runnerNames = {"btcBlockDispatchRunner","btcRechargeSyncRunner"};

        List<Thread> threads = new ArrayList<>();
        for (int i=0; i<runnerNames.length; i++) {
            threads.add(new Thread((Runnable) applicationContext.getBean(runnerNames[i]), runnerNames[i]));
        }
        for (Thread t:threads) {
            t.start();
        }

        boolean loop = true;
        while (loop) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                LOGGER.error("进程结束");
                loop = false;
            }
        }

        for (Thread t:threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                LOGGER.error("等待线程结束异常.", e);
            }
        }

    }
}
