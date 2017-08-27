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
    update_time datetime not null default '9999-12-31 23:59:59.999' comment '更新时间',
    create_time datetime not null default '9999-12-31 23:59:59.999' comment '创建时间',
    primary key (uid),
    unique key index_mobile (mobile),
    unique key index_user_name (user_name),
    unique key index_id_card_no (id_card_no),
    unique key index_email (email)
) engine=InnoDB default charset=utf8 comment='用户表';"
