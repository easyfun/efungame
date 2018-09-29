package com.iudge.ico.framework.task.runnable;

import com.iudge.ico.framework.task.handler.QuartzHandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzRunnable.class);

    private String handler;
    private QuartzHandlerInterface quartzHandler;

    public QuartzRunnable(String handler, QuartzHandlerInterface quartzHandler) {
        this.handler = handler;
        this.quartzHandler = quartzHandler;
    }

    @Override
    public void run() {
        try {
            quartzHandler.execute(null);
        } catch (Exception e) {
            LOGGER.error("执行定时任务错误.handler={}", handler, e);
        }
    }
}
