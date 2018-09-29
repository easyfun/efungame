package com.efun.game.task;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import com.efun.game.common.utils.DateUtil;
import com.efun.game.task.entity.dto.TaskDTO;
import com.efun.game.task.entity.po.mysql.TaskPO;
import com.efun.game.task.entity.po.mysql.builder.TaskPOBuilder;

@Service
public class TaskManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);
	
	@Autowired
	private StringRedisTemplate taskRedisTemplate;
	
	@Autowired
	@Qualifier("produceTaskScript")
	private RedisScript<String> produceTaskScript;
	
	public boolean produceTask(TaskDTO taskDTO) {
    	TaskPO taskPO = TaskPOBuilder.buildTaskPO(taskDTO);
    	return produceTask(taskPO);
    }
    
    private boolean produceTask(TaskPO taskPO) {
    	try {
	    	taskRedisTemplate.execute(produceTaskScript,
	    			new ArrayList<String>(), 
	    			"t_task",
	    			taskPO.getTaskKey(),
	    			taskPO.getHandler(),
	    			taskPO.getParam(),
	    			taskPO.getStatus(),
	    			String.valueOf(taskPO.getRetryStrategy().getValue()),
	    			String.valueOf(taskPO.getRetryInterval()),
	    			String.valueOf(taskPO.getMaxRetryTime()),
	    			DateUtil.formatDate(taskPO.getNextTime(), DateUtil.YYYY_MM_DD_HH_MM_SS),
	    			DateUtil.formatDate(taskPO.getLastTime(), DateUtil.YYYY_MM_DD_HH_MM_SS),
	    			DateUtil.formatDate(taskPO.getFirstTime(), DateUtil.YYYY_MM_DD_HH_MM_SS),
	    			DateUtil.formatDate(taskPO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS),
	    			DateUtil.formatDate(taskPO.getUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS),
					String.valueOf(taskPO.getNextTime().getTime()));
    	} catch (Throwable e) {
    		LOGGER.error("produce task fail.", e);
    		return false;
    	}
//    	LOGGER.debug("produce task success: taskPO={}", JSON.toJSONString(taskPO));
    	return true;
    }
}
