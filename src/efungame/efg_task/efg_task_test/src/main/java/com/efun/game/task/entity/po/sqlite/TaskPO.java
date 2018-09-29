//package com.efun.game.task.entity.po.sqlite;
//
//import java.util.Date;
//
//import com.efun.game.task.enums.RetryStrategy;
//
//public class TaskPO {
//    private Long taskId;
//
//    private String taskKey;
//    
//    private String handler;
//
//    private String param;
//
//    private String status;
//
//    private RetryStrategy retryStrategy;
//
//    private Integer retryInterval;
//
//    private Integer maxRetryTime;
//
//    private Date nextTime;
//    
//    private Date lastTime;
//    
//    private Date firstTime;
//
//    private Date createTime;
//
//    private Date updateTime;
//
//    public Long getTaskId() {
//        return taskId;
//    }
//
//    public void setTaskId(Long taskId) {
//        this.taskId = taskId;
//    }
//
//    public String getHandler() {
//        return handler;
//    }
//
//    public void setHandler(String handler) {
//        this.handler = handler == null ? null : handler.trim();
//    }
//
//    public String getParam() {
//        return param;
//    }
//
//    public void setParam(String param) {
//        this.param = param == null ? null : param.trim();
//    }
//
//    public RetryStrategy getRetryStrategy() {
//        return retryStrategy;
//    }
//
//    public void setRetryStrategy(RetryStrategy retryStrategy) {
//        this.retryStrategy = retryStrategy;
//    }
//
//    public Integer getRetryInterval() {
//        return retryInterval;
//    }
//
//    public void setRetryInterval(Integer retryInterval) {
//        this.retryInterval = retryInterval;
//    }
//
//    public Integer getMaxRetryTime() {
//        return maxRetryTime;
//    }
//
//    public void setMaxRetryTime(Integer maxRetryTime) {
//        this.maxRetryTime = maxRetryTime;
//    }
//
//	public Date getNextTime() {
//		return nextTime;
//	}
//
//	public void setNextTime(Date nextTime) {
//		this.nextTime = nextTime;
//	}
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//	public String getTaskKey() {
//		return taskKey;
//	}
//
//	public void setTaskKey(String taskKey) {
//		this.taskKey = taskKey;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public Date getLastTime() {
//		return lastTime;
//	}
//
//	public void setLastTime(Date lastTime) {
//		this.lastTime = lastTime;
//	}
//
//	public Date getFirstTime() {
//		return firstTime;
//	}
//
//	public void setFirstTime(Date firstTime) {
//		this.firstTime = firstTime;
//	}
//
//}
