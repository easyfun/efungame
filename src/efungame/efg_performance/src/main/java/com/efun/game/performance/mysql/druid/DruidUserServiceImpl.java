package com.efun.game.performance.mysql.druid;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.efun.game.common.id.IdUtils;
import com.efun.game.performance.mysql.mybatis.entity.User;

@Service("druidUserServiceImpl")
public class DruidUserServiceImpl implements DruidUserService {
	private static final Logger logger=LoggerFactory.getLogger(DruidUserServiceImpl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Resource
	private DataSource dataSource;

	
	@Override
	public void insert() {
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
		} catch (Exception e1) {
			logger.error("fail connect to mysql.", e1);
			System.exit(-1);
		}
		
		try {
			insert(connection);
			connection.commit();
		} catch (Exception e) {
			logger.error("fail userService.insert", e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("fail rollback.", e);
				System.exit(-1);
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error("fail close connection.", e);
			System.exit(-1);
		}
	}

	private void insert(Connection connection) throws SQLException {
		Statement stat=connection.createStatement();
		String insert=buildInsert();
//		logger.info("insertSql={}", insert);
		int count = stat.executeUpdate(insert);
//		logger.info("count={}", count);
		stat.close();
	}

	private String buildInsert() {
		Date now=new Date();
		User user=buildUser(now);
		String nowStr = sdf.format(now);
		String insert="insert into t_user (uid, mobile, user_name,email, "
				+ "id_card_no, id_card_type,id_card_status, password, "
				+ "user_status, update_time, create_time) values (" 
				+ user.getUid()
				+ "," + user.getMobile()
				+ "," + user.getUserName()
				+ "," + user.getEmail()
				+ "," + user.getIdCardNo()
				+ ", 0, 0, 'easyfun', 0, '" + nowStr + "', '"+ nowStr + "')";
		return insert;
	}
	
	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	
	private User buildUser(Date now) {
		User user = new User();
		user.setUid(IdUtils.getInstance().createUid());
		user.setMobile(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setUserName(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setEmail(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setIdCardNo(String.valueOf(IdUtils.getInstance().createSessionId()));
		user.setIdCardType((byte) 0);
		user.setIdCardStatus((byte) 0);
		user.setPassword("easyfun");
		user.setUserStatus((byte) 0);
		user.setUpdateTime(now);
		user.setCreateTime(now);
		return user;
	}
}
