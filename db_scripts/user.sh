#!/bin/bash
password=123456
mysql_exec="mysql -uroot -p$password"
db=user

$mysql_exec -e "create database $db;"
#用户信息表
$mysql_exec $db -e "create table t_user
(
    uid bigint not null comment 'uid',
    mobile varchar(20) default null comment '手机号',
    user_name varchar(64) not null comment '用户名',
    email varchar(200) default null comment '邮箱',
    id_card_no varchar(100) default null comment '证件号码',
    id_card_type tinyint not null default '0' comment '证件类型: 0-未知类型; 1-中国大陆居民身份证; 2-台湾居民来往大陆通行证; 3-港澳居民来往内地通行证',
    id_card_status tinyint not null default '0' comment '实名状态: 0-未知; 1-未实名; 2-已实名',
    password varchar(128) not null comment '密码',
    user_status tinyint not null default '0' comment '状态: 0-正常; 1-锁定; 2-删除; 3-黑名单',
    update_time datetime not null default '0000-00-00 00:00:00' comment '更新时间',
    create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    primary key (uid),
    unique key index_mobile (mobile),
    unique key index_user_name (user_name),
    unique key index_id_card_no (id_card_no),
    unique key index_email (email)
) engine=InnoDB default charset=utf8 comment='用户表';"

#用户详细信息表
$mysql_exec $db -e "create table t_user_detail
(
    uid bigint not null comment 'uid',
    nick_name varchar(64) not null default '' comment '昵称',
    head_picture_url varchar(256) not null default '' comment '头像地址',
    gender tinyint not null default '0' comment '性别: 0-未知; 1-女; 2-男',
    real_name varchar(40) not null default '' comment '真实姓名',
    birthday datetime not null default '0000-00-00 00:00:00' comment '生日',
    age int not null default '-1' comment '年龄: -1未知',
    marriage_status tinyint default '0' comment '婚姻状态: 0-未婚; 1-已婚; 2-保密',
    education varchar(10) not null default '0' comment '学历: 0-未知',
    security_level tinyint not null default '0' comment '账户安全等级: 0-未知',
    city_code varchar(30) not null default '' comment '城市code',
    pro_code varchar(30) not null default '' comment '省份code',
    sign_up_ip varchar(64) not null default '' comment '注册ip',
    sign_up_channel varchar(64) default null comment '注册渠道号',
    sign_up_app_type tinyint not null default '0' comment 'app类型: 0-未知; 1-Web; 2-IOS; 3-Andriod',
    sign_up_date datetime not null default '0000-00-00 00:00:00' comment '注册时间',
    update_time datetime not null default '0000-00-00 00:00:00' comment '更新时间',
    create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    PRIMARY KEY (uid)
) engine=InnoDB default charset=utf8 comment='用户详细信息';"

#用户属性表
$mysql_exec $db -e "create table t_user_attr
(
    uid bigint not null comment 'uid',
    field_name varchar(64) not null comment '属性',
    field_value varchar(250) not null comment '值',
    update_time datetime not null default '0000-00-00 00:00:00' comment '更新时间',
    create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    primary key (uid)
) engine=InnoDB default charset=utf8 comment='用户属性';"

#用户登录历史
$mysql_exec $db -e "create table t_user_sign_in_log
(
    uid bigint not null comment 'uid',
    session_id bigint not null comment '会话id',
    sign_in_ip varchar(64) not null default '' comment '登录ip',
    sign_in_status tinyint not null comment '登录状态: 0-失败; 1-成功',
    sign_in_app_type tinyint default '0' comment 'app类型: 0-未知; 1-Web; 2-IOS; 3-Andriod',
    sign_in_time  datetime not null default '0000-00-00 00:00:00' comment '登录时间',
    create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    primary key(uid),
    unique key index_session_id (session_id)
)engine=InnoDB default charset=utf8;"

#uid与idno关系映射表
$mysql_exec $db -e "create table t_uid_with_id_card_no
(
    uid bigint not null comment 'uid',
    id_card_no varchar(100) not null comment '身份证号',
    id_card_type tinyint not null default '0' comment '证件类型: 0-未知类型; 1-中国大陆居民身份证; 2-台湾居民来往大陆通行证; 3-港澳居民来往内地通行证',
    used_status tinyint not null default '0' comment '使用状态: 0-正常; 1-禁用',
    primary key (uid),
    unique key index_id_card_no(id_card_no)
) engine=InnoDB default charset=utf8 comment='uid与id_card_no关联表';"

#uid与mobile关系映射表
$mysql_exec $db -e "create table t_uid_with_mobile
(
    uid bigint not null comment 'uid',
    mobile varchar(20) not null comment '手机号',
    used_status tinyint not null default '0' comment '使用状态: 0-正常; 1-禁用',
    primary key (uid),
    unique key index_mobile (mobile)
)engine=InnoDB default charset=utf8 comment='uid与mobile关联表';"

#uid与user_name关系映射表
$mysql_exec $db -e "create table t_uid_with_user_name
(
    uid bigint not null comment 'uid',
    user_name varchar(64) not null comment '用户名',
    used_status tinyint not null default '0' comment '使用状态: 0-正常; 1-禁用',
    primary key (uid),
    unique key index_user_name (user_name)
)engine=InnoDB default charset=utf8 comment='uid与user_name关联表';"

#uid与user_name关系映射表
$mysql_exec $db -e "create table t_uid_with_email
(
    uid bigint not null comment 'uid',
    email varchar(64) not null comment '邮箱',
    used_status tinyint not null default '0' comment '使用状态: 0-正常; 1-禁用',
    primary key (uid),
    unique key index_email (email)
)engine=InnoDB default charset=utf8 comment='uid与email关联表';"