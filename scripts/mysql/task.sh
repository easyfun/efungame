#!/bin/bash
password=easyfun
mysql_exec="mysql -uroot -p$password"
db=task

$mysql_exec -e "create database task;"

# use task;
$mysql_exec $db -e "create table t_task (
  task_id                bigint unsigned not null comment '任务id',
  task_key               varchar(64) not null comment '任务key',
  handler           varchar(128) not null default '' comment '任务处理器',#逻辑可变
  param             varchar(128) not null default '' comment '请求参数',
  status            varchar(16) not null default '' comment '处理状态',
  #version           varchar(16) not null default '0.0.0' comment '处理器版本号',
  ###child_task        varchar(512) not null default '' comment '子任务',#列表可变
  retry_strategy  tinyint unsigned not null default '1' comment '重试策略',
  retry_interval  int unsigned not null default '300' comment '重试时间间隔:豪秒',
  max_retry_time  int unsigned not null default '3' comment '最大重试次数',
  next_time         datetime not null default '0000-00-00 00:00:00' comment '下次执行时间',
  last_time         datetime not null default '0000-00-00 00:00:00' comment '最新执行时间',
  first_time        datetime not null default '0000-00-00 00:00:00' comment '首次执行时间',
  create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
  update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
  primary key (task_id),
  index idx_task_key_handler(task_key,handler),
  index idx_task_key (task_key),
  index idx_first_time (first_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '任务信息表';"

$mysql_exec $db -e "create table t_child_task (
  id                bigint unsigned not null auto_increment comment 'id',
  task_id           bigint unsigned not null comment '任务id',
  task_key          varchar(64) not null comment '任务key',
  handler           varchar(128) not null default '' comment '任务处理器',#逻辑可变
  child_handler     varchar(128) not null default '' comment '子任务处理器',#逻辑可变
  status            varchar(16) not null default '' comment '处理状态',
  last_time         datetime not null default '0000-00-00 00:00:00' comment '最新执行时间',
  first_time        datetime not null default '0000-00-00 00:00:00' comment '首次执行时间',
  create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
  update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
  primary key (id),
  index idx_task_id (task_id),
  index idx_task_key_handler (task_key,handler),
  index idx_task_key (task_key),
  index idx_first_time (first_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '子任务信息表';"

$mysql_exec $db -e "create table t_task_change (
  id                    bigint unsigned not null auto_increment comment 'id',
  task_id               bigint unsigned not null comment '任务id',
  task_key              varchar(64) not null comment '任务key',
  handler               varchar(128) not null default '' comment '任务处理器',#逻辑可变
  change_type           tinyint unsigned not null default 0 comment '变更类型',
  status                varchar(16) not null default '' comment '处理状态',
  error_code            varchar(24) not null default '' comment '错误码',
  error_desc            varchar(200) default null comment '错误描述',
  apply_time            datetime not null default '0000-00-00 00:00:00' comment '申请时间',
  finish_time           datetime not null default '0000-00-00 00:00:00' comment '完成时间',
  create_time           datetime not null default '0000-00-00 00:00:00' comment '创建时间',
  update_time           datetime not null default '0000-00-00 00:00:00' comment '更新时间',
  primary key (id),
  index idx_task_id (task_id),
  index idx_task_key_handler (task_key,handler),
  index idx_task_key (task_key),
  index idx_apply_time (apply_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '任务变更信息表';"


### 简化版任务
create table t_task (
  `id` bigint unsigned not null auto_increment comment 'id',
  `task_key` VARCHAR(80) not null comment '任务key',
  `param` VARCHAR(1024) default null comment '参数',
  `handler` VARCHAR(100) not null comment '处理器',
  `business` VARCHAR(100) NOT NULL COMMENT '业务',
  `retry_strategy` int not null comment '重试策略',
  `retry_interval` int not null comment '重试间隔ms',
  `max_retry_times` int not null comment '最大重试次数',
  `task_status` int not null comment '执行状态',
  `progress` INT NOT NULL DEFAULT -1 COMMENT '子任务执行速度',
  `retried_times` int not null comment '已重试次数',
  `created_time` datetime not null comment '创建时间',
  `first_time` datetime default null comment '首次执行时间',
  `last_time` datetime default null comment '最近执行时间',
  `next_time` datetime not null comment '下次执行时间',
  `done_time` datetime default null comment '完成时间',
  `updated_time` datetime not null comment '更新时间',
  primary key (`id`),
  index `idx_task_key` (`task_key`),
  INDEX `idx_created_time` (`created_time`),
  index `idx_done_time` (`done_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '任务信息表';

