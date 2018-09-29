package com.efun.game.performance.sqlite.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.efun.game.common.id.IdUtils;
import com.efun.game.performance.common.Stats;
import com.efun.game.performance.sqlite.TaskPO;

@Service("jdbcTaskServiceImpl")
public class JdbcTaskServiceImpl implements JdbcTaskService {
	private static final Logger logger=LoggerFactory.getLogger(JdbcTaskServiceImpl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void insert(Connection connection) {
		try {
			insert2(connection);
			connection.commit();
			Stats.addSuccess(1);
		} catch (Exception e) {
			Stats.addFail(1);
			logger.error("fail userService.insert", e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("fail rollback.", e);
				System.exit(-1);
			}
		}
	}

	private void insert2(Connection connection) throws SQLException {
		Statement stat=connection.createStatement();
		String insert=buildInsert();
//		logger.info("insertSql={}", insert);
		int count = stat.executeUpdate(insert);
//		logger.info("count={}", count);
		stat.close();
	}
	
	private String buildInsert() {
		Date now=new Date();
		TaskPO po=buildTaskPO(now);
		String nowStr = sdf.format(now);
		String insert = "insert into t_task (task_id, "
				+ "task_key, "
				+ "handler, "
				+ "param, "
				+ "status, "
				+ "retry_strategy, "
				+ "retry_interval, "
				+ "max_retry_time, "
				+ "next_time, "
				+ "last_time, "
				+ "first_time, "
				+ "create_time, "
				+ "update_time)  values ("
				+ po.getTaskId()
				+ ", " + po.getTaskKey()
				+ ", " + po.getHandler()
				+ ", " + po.getParam()
				+ ", " + po.getStatus()
				+ ", " + po.getRetryStrategy()
				+ ", " + po.getRetryInterval()
				+ ", " + po.getMaxRetryTime()
				+ ", " + now
				+ ", " + now
				+ ", " + now
				+ ", " + now
				+ ", " + now
				+ ")";
		return insert;
	}
	
	private TaskPO buildTaskPO(Date now) {
		TaskPO po = new TaskPO();
		po.setTaskId(IdUtils.getInstance().createFlowId(""));
		po.setTaskKey(String.valueOf(po.getTaskId()));
		po.setHandler(String.valueOf(po.getTaskId()));
		po.setParam(String.valueOf(po.getTaskId()));
		po.setStatus("accepted");
		po.setRetryStrategy(1);
		po.setRetryInterval(6000);
		po.setMaxRetryTime(5);
		po.setNextTime(now);
		po.setLastTime(now);
		po.setFirstTime(now);
		po.setCreateTime(now);
		po.setUpdateTime(now);
		return po;
	}
}
