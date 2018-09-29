package com.efun.game.task.entity.dto.builder;

import java.util.Date;

import com.efun.game.task.entity.dto.TaskDTO;
import com.efun.game.task.enums.RetryStrategy;

public class TaskDTOBuilder {

	public static TaskDTO testBuildTaskDTO() {
		TaskDTO dto = new TaskDTO();
		dto.setTaskKey(String.valueOf(System.nanoTime()));
		dto.setHandler("handler");
		dto.setParam("param");
		dto.setRetryStrategy(RetryStrategy.forever);
		dto.setRetryInterval(1000);
		dto.setMaxRetryTime(10);
		dto.setExecuteTime(new Date());
		return dto;
	}
}
