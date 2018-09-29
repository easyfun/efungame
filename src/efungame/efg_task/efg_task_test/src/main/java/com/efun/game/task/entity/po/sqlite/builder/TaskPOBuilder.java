//package com.efun.game.task.entity.po.sqlite.builder;
//
//import java.util.Date;
//
//import com.efun.game.task.entity.dto.TaskDTO;
//import com.efun.game.task.entity.po.sqlite.TaskPO;
//import com.efun.game.task.enums.RetryStrategy;
//
//public class TaskPOBuilder {
//	public static TaskPO buildTaskPO(TaskDTO taskDTO) {
//		TaskPO taskPO = new TaskPO();
//    	taskPO.setTaskKey(taskDTO.getTaskKey());
//    	taskPO.setHandler(taskDTO.getHandler());
//    	taskPO.setParam(taskDTO.getParam());
//    	taskPO.setStatus("accepted");
//    	taskPO.setRetryStrategy(taskDTO.getRetryStrategy());
//    	taskPO.setRetryInterval(taskDTO.getRetryInterval());
//    	taskPO.setMaxRetryTime(taskDTO.getMaxRetryTime());
//    	taskPO.setNextTime(taskDTO.getExecuteTime());
//    	taskPO.setLastTime(null);
//    	taskPO.setFirstTime(null);
//    	Date current = new Date();
//    	taskPO.setCreateTime(current);
//    	taskPO.setUpdateTime(current);
//    	
//    	return taskPO;
//	}
//	
//	public static TaskPO testBuildTaskPO() {
//		TaskPO po = new TaskPO();
//		po.setTaskId(System.currentTimeMillis());
//		po.setTaskKey(String.valueOf(System.currentTimeMillis()));
//		po.setHandler("RobTicketHandler");
//		po.setParam("GodLuck");
//		po.setStatus("accepted");
//		po.setRetryStrategy(RetryStrategy.forever);
//		po.setRetryInterval(1000);
//		po.setMaxRetryTime(5);
//		Date current = new Date();
//		po.setNextTime(current);
//		po.setFirstTime(current);
//		po.setLastTime(current);
//		po.setCreateTime(current);
//		po.setUpdateTime(current);
//		
//		return po;
//	}
//
//}
