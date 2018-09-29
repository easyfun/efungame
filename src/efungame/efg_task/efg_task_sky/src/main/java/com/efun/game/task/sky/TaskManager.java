package com.efun.game.task.sky;

import com.alibaba.fastjson.JSON;
import com.efun.game.task.sky.entity.TaskPO;
import com.efun.game.task.sky.entity.builder.TaskPOBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/27.
 * 任务生产过程
 * 1. 插入任务检查mq
 * 2. 任务检查，重复性检查
 * 3. 插入redis
 * 3. 插入任务mq
 */
@Service
public class TaskManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);
    private static final String JSON_CHAR_SET = "UTF-8";

    @Autowired
    private StringRedisTemplate taskRedisTemplate;

    @Autowired
    private AmqpTemplate taskAmqpTemplate;

    @Value("${queue.name.task}")
    private String taskQueue;

    @Value("${queue.name.retry.task}")
    private String retryTaskQueue;

    /**
     * 安全
     * 1. 插入任务检查mq
     * @param task
     * @return
     */
    public boolean produceTask(Task task) {
        if (!validateTask(task)) {
            return false;
        }
        return true;
    }

    /**
     * 不安全，需要调用方确定key唯一
     * 1. 插入redis
     * 2. 插入任务mq
     * @param task
     * @return
     */
    public boolean fastProduceTask(Task task) {
        if (!validateTask(task)) {
            LOGGER.debug("生产任务错误，未通过参数校验");
            return false;
        }

        // 1.插入redis
        if (!safeInsertRedis(task)) {
            return false;
        }

        // 2.插入任务mq
//        if (!safeInsertMq(task)) {
//            return false;
//        }
        return true;
    }

    /**
     * 重试任务
     * @param taskPO
     * @return
     */
    public boolean produceRetryTask(TaskPO taskPO) {
        if (!validateTaskPO(taskPO)) {
            return false;
        }

        // 1.更新redis
        if (!safeUpdateRedis(taskPO)) {
            return false;
        }

        // 2.插入任务mq
//        if (!safeInsertRetryMq(taskPO)) {
//            return false;
//        }
        return true;
    }

    private boolean validateTask(Task task) {
        if (StringUtils.isBlank(task.getKey()) || StringUtils.isBlank(task.getHandler()) || null == task.getRetryStrategy()) {
            return  false;
        }
        return true;
    }

    private boolean validateTaskPO(TaskPO taskPO) {
        if (StringUtils.isBlank(taskPO.getKey()) || StringUtils.isBlank(taskPO.getHandler()) || null == taskPO.getRetryStrategy()) {
            return  false;
        }
        return true;
    }

    /**
     * 任务插入redis失败，增加监控
     * @param task
     * @return
     */
    private boolean safeInsertRedis(Task task) {
        if (!insertRedis(task)) {
            // TODO: 任务插入redis失败，增加监控
            return false;
        }
        return true;
    }

    private boolean insertRedis(Task task) {
        try {
            Map<String, String> redisMap = TaskPOBuilder.buildTaskPORedisMap(task);
            String redisKey = getRedisKey(task.getKey());
            taskRedisTemplate.opsForHash().putAll(redisKey, redisMap);
        } catch (Exception e) {
            LOGGER.error("生产任务错误,插入redis失败.", e);
            return false;
        }
        return true;
    }

    private boolean safeUpdateRedis(TaskPO taskPO) {
        try {
            Map<String, String> redisMap = TaskPOBuilder.buildTaskPORedisMap(taskPO);
            String redisKey = getRedisKey(taskPO.getKey());
            taskRedisTemplate.opsForHash().putAll(redisKey, redisMap);
        } catch (Exception e) {
            LOGGER.error("生产任务错误,插入redis失败.", e);
            return false;
        }
        return true;
    }

    private String getRedisKey(String key) {
        return "t_task:info:"+key;
    }

    private boolean safeInsertMq(Task task) {
        try {
            if (!insertMq(buildMessage(task))) {
                return false;
            }
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error("插入任务mq失败，创建Message对象失败.task={}", JSON.toJSONString(task), e);
            return false;
        }
        return true;
    }

    private boolean insertMq(Message message) {
        taskAmqpTemplate.send(taskQueue, message);
        return true;
    }

    /**
     * 重试消息
     * @param taskPO
     * @return
     */
    private boolean safeInsertRetryMq(TaskPO taskPO) {
        try {
            if (!insertRetryMq(buildRetryMessage(taskPO))) {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("插入任务mq失败，创建Message对象失败.task={}", JSON.toJSONString(taskPO), e);
            return false;
        }
        return true;
    }

    /**
     * 重试消息
     * @param message
     * @return
     */
    private boolean insertRetryMq(Message message) {
        taskAmqpTemplate.send(retryTaskQueue, message);
        return true;
    }

    private Message buildMessage(Task task) throws UnsupportedEncodingException {
        byte[] body = JSON.toJSONString(task).getBytes(JSON_CHAR_SET);
        Message message = MessageBuilder.withBody(body)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding(JSON_CHAR_SET)
                .setContentLength(body.length)
                .build();
        return message;
    }

    /**
     * 重试消息
     * @param taskPO
     * @return
     * @throws UnsupportedEncodingException
     */
    private Message buildRetryMessage(TaskPO taskPO) throws UnsupportedEncodingException {
        byte[] body = JSON.toJSONString(taskPO).getBytes(JSON_CHAR_SET);
        Message message = MessageBuilder.withBody(body)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding(JSON_CHAR_SET)
                .setContentLength(body.length)
                .setExpiration(String.valueOf(taskPO.getRetryInterval()))
                .build();
        return message;
    }

}
