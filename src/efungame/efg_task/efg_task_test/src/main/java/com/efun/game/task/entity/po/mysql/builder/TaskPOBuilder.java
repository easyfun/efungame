package com.efun.game.task.entity.po.mysql.builder;

import java.util.Date;

import com.efun.game.task.entity.dto.TaskDTO;
import com.efun.game.task.entity.po.mysql.TaskPO;

public class TaskPOBuilder {
	public static TaskPO buildTaskPO(TaskDTO dto) {
		TaskPO po = new TaskPO();
		po.setTaskId(null);
		po.setTaskKey(dto.getTaskKey());
		po.setHandler(dto.getHandler());
		po.setParam(dto.getParam());
		po.setStatus("accepted");
		po.setRetryStrategy(dto.getRetryStrategy());
		po.setRetryInterval(dto.getRetryInterval());
		po.setMaxRetryTime(dto.getMaxRetryTime());
		po.setNextTime(dto.getExecuteTime());
		po.setLastTime(null);
		po.setFirstTime(null);
		Date current = new Date();
		po.setCreateTime(current);
		po.setUpdateTime(current);
		return po;
	}
}
