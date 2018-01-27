#!/bin/bash
# password=easyfun
# sqlite_exec="sqlite -uroot -p$password"
# db=task

# $sqlite_exec -e "create database task;"

# use task;
create table t_task (
  task_id                bigint unsigned not null ,
  task_key               varchar(64) not null ,
  handler           varchar(128) not null default '' ,
  param             varchar(128) not null default '' ,
  status            varchar(16) not null default '' ,
  retry_strategy  tinyint unsigned not null default '1' ,
  retry_interval  int unsigned not null default '300' ,
  max_retry_time  int unsigned not null default '3' ,
  next_time         datetime not null default '0000-00-00 00:00:00' ,
  last_time         datetime not null default '0000-00-00 00:00:00' ,
  first_time        datetime not null default '0000-00-00 00:00:00' ,
  create_time        datetime not null default '0000-00-00 00:00:00' ,
  update_time        datetime not null default '0000-00-00 00:00:00' ,
  primary key (task_id)
);

create unique index idx_task_key_handler on t_task (task_key, handler);
create index idx_task_key on t_task (task_key);
create index idx_first_time on t_task (first_time);

create table t_child_task (
  id                bigint unsigned not null auto_increment ,
  task_id           bigint unsigned not null ,
  task_key          varchar(64) not null ,
  handler           varchar(128) not null default '' ,
  child_handler     varchar(128) not null default '' ,
  status            varchar(16) not null default '' ,
  last_time         datetime not null default '0000-00-00 00:00:00' ,
  first_time        datetime not null default '0000-00-00 00:00:00' ,
  create_time        datetime not null default '0000-00-00 00:00:00' ,
  update_time        datetime not null default '0000-00-00 00:00:00' ,
  primary key (id)
);

create index idx_task_id on t_child_task (task_id);
create index idx_task_key_handler on t_child_task (task_key,handler);
create index idx_task_key on t_child_task (task_key);
create index idx_first_time on t_child_task (first_time;


create table t_task_change (
  id                    bigint unsigned not null auto_increment ,
  task_id               bigint unsigned not null ,
  task_key              varchar(64) not null ,
  handler               varchar(128) not null default '' ,
  change_type           tinyint unsigned not null default 0 ,
  status                varchar(16) not null default '' ,
  error_code            varchar(24) not null default '' ,
  error_desc            varchar(200) default null ,
  apply_time            datetime not null default '0000-00-00 00:00:00' ,
  finish_time           datetime not null default '0000-00-00 00:00:00' ,
  create_time           datetime not null default '0000-00-00 00:00:00' ,
  update_time           datetime not null default '0000-00-00 00:00:00' ,
  primary key (id)
);

create index idx_task_id on t_task_change (task_id);
create index idx_task_key_handler on t_task_change (task_key,handler);
create index idx_task_key on t_task_change (task_key);
create index idx_apply_time on t_task_change (apply_time;
