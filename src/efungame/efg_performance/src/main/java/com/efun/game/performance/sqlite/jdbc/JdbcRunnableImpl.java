package com.efun.game.performance.sqlite.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.efun.game.performance.common.Stats;

class JdbcRunnableImpl implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JdbcRunnableImpl.class);
	private final ClassPathXmlApplicationContext applicationContext;

	private final int maxCount;

	public JdbcRunnableImpl(ClassPathXmlApplicationContext applicationContext, int maxCount) {
		this.applicationContext = applicationContext;
		this.maxCount = maxCount;
	}

	// public JdbcRunnableImpl(int maxCount) {
	// this.maxCount = maxCount;
	// }

	@Override
	public void run() {

		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
		} catch (Exception e1) {
			logger.error("fail connect to mysql.", e1);
			System.exit(-1);
		}

		JdbcTaskService service = (JdbcTaskService) applicationContext.getBean("jdbcTaskServiceImpl");

		int i = 0;
		while (i++ < maxCount) {
			try {
				service.insert(connection);
				Stats.addSuccess(1);
			} catch (Exception e) {
				Stats.addFail(1);
				logger.error("fail userService.insert", e);
			}
		}

	}

	private Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
		// Properties props = new Properties();
		// InputStream inputStream = new FileInputStream(
		// 		"E:\\github\\java\\efungame\\src\\efungame\\efg_performance\\src\\main\\resources\\properties\\datasource.properties");
		// props.load(inputStream);
		// String drivers = props.getProperty("app.jdbc.driverClass");
		// if (null != drivers) {
		// 	System.setProperty("jdbc.drivers", drivers);
		// }
		// String url = props.getProperty("app.jdbc.url");
		// String username = props.getProperty("app.jdbc.user");
		// String password = props.getProperty("app.jdbc.password");
		// logger.info("url={}, username={}, password={}", url, username, password);
		// return DriverManager.getConnection(url, username, password);
		
		Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:D:\\run\\task.db");
	}
}