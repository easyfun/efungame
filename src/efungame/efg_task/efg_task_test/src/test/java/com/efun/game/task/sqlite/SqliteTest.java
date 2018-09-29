package com.efun.game.task.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class SqliteTest {
    private static final String CLASS_NAME = "org.sqlite.JDBC";
//    private static final String DB_URL = "jdbc:sqlite:/home/easyfun/data/task.db";
    private static final String DB_URL = "jdbc:sqlite:D:\\run\\task.db";

    @Test
    public void create() throws Exception {
        System.out.println("sqlite hello");
        Class.forName(CLASS_NAME);
        Connection conn = DriverManager.getConnection(DB_URL);

        Statement smt1 = conn.createStatement();
        smt1.setQueryTimeout(30);
        String sql = "create table user ("
        +"id bigint unsigned,"
        +"name varchar(64) not null,"
        +"age int unsigined not null,"
        +"primary key (id))";
        // String sql = "insert into user (id,name,age) "
        // + "values ("
        // + "null, 'easyfun', 10"
        // + ")";
        // ResultSet rSet = statement.executeQuery(sql);
        // System.out.println(rSet);
        // smt1.executeUpdate(sql);

/*        sql = "insert into user (id, name, age) "
            + "values (1,'easyfun',29);";*/

/*        sql = "create table t_task ("
        + "  task_id                bigint unsigned not null comment '任务id',"
        + "  task_key               varchar(64) not null comment '任务key',"
        + "  handler           varchar(128) not null default '' comment '任务处理器',"
        + "  param             varchar(128) not null default '' comment '请求参数',"
        + "  status            varchar(16) not null default '' comment '处理状态',"
        + "  retry_strategy  tinyint unsigned not null default '1' comment '重试策略',"
        + "  retry_interval  int unsigned not null default '300' comment '重试时间间隔:豪秒',"
        + "  max_retry_time  int unsigned not null default '3' comment '最大重试次数',"
        + "  next_time         datetime not null default '0000-00-00 00:00:00' comment '下次执行时间',"
        + "  last_time         datetime not null default '0000-00-00 00:00:00' comment '最新执行时间',"
        + "  first_time        datetime not null default '0000-00-00 00:00:00' comment '首次执行时间',"
        + "  create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',"
        + "  update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',"
        + "  primary key (task_id),"
        + "  index idx_task_key_handler(task_key,handler),"
        + "  index idx_task_key (task_key),"
        + "  index idx_first_time (first_time)"
        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '任务信息表';"; */

        System.out.println(sql);
        smt1.executeUpdate(sql);

        // Statement smt2 = conn.createStatement();
        // ResultSet rs = smt1.executeQuery( "SELECT * FROM user" );
        // while ( rs.next() ) {
        //    int id = rs.getInt("id");
        //    String  name = rs.getString("name");
        //    int age  = rs.getInt("age");
        //    System.out.println( "ID = " + id );
        //    System.out.println( "NAME = " + name );
        //    System.out.println( "AGE = " + age );
        //    System.out.println();
        // }
        // rs.close();
        smt1.close();
        conn.close();

    }

}