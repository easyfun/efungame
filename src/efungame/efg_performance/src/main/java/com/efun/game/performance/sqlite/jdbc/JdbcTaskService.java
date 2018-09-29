package com.efun.game.performance.sqlite.jdbc;

import java.sql.Connection;

public interface JdbcTaskService {
	void insert(Connection connection);
}
