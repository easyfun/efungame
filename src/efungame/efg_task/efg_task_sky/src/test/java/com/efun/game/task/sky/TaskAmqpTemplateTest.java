package com.efun.game.task.sky;

import com.alibaba.fastjson.JSON;
import com.efun.game.commontest.SpringTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/4.
 */
public class TaskAmqpTemplateTest extends SpringTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskAmqpTemplateTest.class);

    @Autowired
    private AmqpTemplate taskAmqpTemplate;

    @Test
    public void send() {
        taskAmqpTemplate.send("queue.task", new Message("12.34".getBytes(), new MessageProperties()));

        // mq异步投递，需要等待
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendExpiration() throws UnsupportedEncodingException {
        Map<String, String> b = new HashMap<>();
        b.put("name", "easyfun");
        byte[] body = JSON.toJSONString(b).getBytes("UTF-8");
        LOGGER.debug("body={}", JSON.toJSONString(b));
        Message message = MessageBuilder.withBody(body)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("UTF-8")
                .setContentLength(body.length)
                .setExpiration("3000")
                .build();
        taskAmqpTemplate.send("queue.retry.task", message);

        message = MessageBuilder.withBody(body)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("UTF-8")
                .setContentLength(body.length)
                .setExpiration("60000")
                .build();
        taskAmqpTemplate.send("queue.retry.task", message);

        // mq异步投递，需要等待
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
