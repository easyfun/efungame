# 任务调度系统

## 第一章 系统概述
- 订单系统在电商，银行，证券交易等诸多领域广泛采用，通常采用异步处理模式
- 任务调度系统的设计目的是方便服务端异步接口的开发，先将合法请求入库，启动异步处理任务，同时返回“请求已接受”应答结果，处理任务完成业务之后更新业务处理状态，通知调用者处理结果，调用者轮循查询业务处理结果直到业务处理完成
- 高稳定，任务不丢失，不重复执行，无并发问题
- 高可用
- 高性能，尽量少与db交互，尽量少交互信息量，批量查询

## 第二章 工作原理
- 基于生产者－消费者模式
- 利用redis的hash,zset数据类型创建工作队列
- 数据存储redis、mysql，sqlite

### 系统框图
![系统框架](./resources/task_dispatch_system/task_dispatch_system_architecture_diagram.png)

## 第三章 数据结构

- task: {childTask1,childTask2,...}
- taskHandler: {childTaskHandler1,childTaskHandler2,...}

### mysql表设计

    create database task;
    use task;
    create table t_task (
      id                bigint not null comment '任务id',
      handler           varchar(128) not null default '' comment '任务处理器',#逻辑可变
      param             varchar(128) not null default '' comment '请求参数',
      status            varchar(16) not null comment '处理状态',
      #version           varchar(16) not null default '0.0.0' comment '处理器版本号',
      ###child_task        varchar(512) not null default '' comment '子任务',#列表可变
      retry_strategy  tinyint not null default '1' comment '重试策略',
      retry_interval  int not null default '300' comment '重试时间间隔:豪秒',
      max_retry_time  int not null default '3' comment '最大重试次数',
      next_time         datetime not null default '0000-00-00 00:00:00' comment '下次执行时间',
      last_time         datetime not null default '0000-00-00 00:00:00' comment '最新执行时间',
      first_time        datetime not null default '0000-00-00 00:00:00' comment '首次执行时间',
      create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
      update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
      primary key (id),
      index idx_first_time (first_time)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '任务信息表';

    create table t_child_task (
      id                bigint not null comment '任务id',
      handler           varchar(128) not null default '' comment '任务处理器',#逻辑可变
      child_handler     varchar(128) not null default '' comment '子任务处理器',#逻辑可变
      status            varchar(16) not null default '' comment '处理状态',
      last_time         datetime not null default '0000-00-00 00:00:00' comment '最新执行时间',
      first_time        datetime not null default '0000-00-00 00:00:00' comment '首次执行时间',
      create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
      update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
      primary key (id),
      index idx_first_time (first_time)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '子任务信息表';

    create table t_child_change (
      id                    bigint not null comment '任务id',
      handler               varchar(128) not null default '' comment '任务处理器',#逻辑可变
      change_type           tinyint unsigined not null default '0' comment '变更类型',
      status                varchar(16) not null default '' comment '处理状态',
      error_code            varchar(24) not null default '' comment '错误码',
      error_desc            varchar(200) default null comment '错误描述',
      apply_time            datetime not null default '0000-00-00 00:00:00' comment '申请时间',
      finish_time           datetime not null default '0000-00-00 00:00:00' comment '完成时间',
      create_time           datetime not null default '0000-00-00 00:00:00' comment '创建时间',
      update_time           datetime not null default '0000-00-00 00:00:00' comment '更新时间',
      primary key (id),
      index idx_apply_time (apply_time)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '子任务信息表';

### redis数据结构

#### 任务信息
hash类型，zset类型，采用方案一

方案一

	t_task:$id
        handler
        param
        status
        retry_strategy
        retry_interval
        next_time
        last_time
        first_time

方案二

    t_task
        $id {"handler":"","param":"","status":"","retryStrategy":"","retryInterval":"","nextTime":"","lastTime":"","firstTime":""}


#### 子任务信息
hash类型，采用方案二，检查子任务状态

方案一
    t_child_task:$id:$child_handler
        handler
        status
        last_time
        first_time

方案二

    t_child_task:$id
        $child_handler:handler
        $child_handler:status
        $child_handler:last_time
        $child_handler:first_time

方案三

    t_child_task:$id
        $child_handler {"handler":"","status":"","lastTime":"","firstTime":""}

#### 任务变更
hash类型

    t_task_change:$id
        $change_type:changeType 1
        $change_type:status 0
        $change_type:errorCode 0
        $change_type:errorDesc ''

### 处理队列

- 日集合,
- 执行队列,zset,running:{(id1 time),(id2 time),...}
- 等待队列,zset,waiting:{(id1 time),(id2 time),...}
- 挂起队列,zset,pause:{(id1 time),(id2 time),...}
- 成功集合,zset,sucess:{(id1 time),(id2 time),...}
- 失败集合,zset,fail:{(id1 time),(id2 time),...}
- 挂起取消集合,zset,failList:{(id1 time),(id2 time),...}

### 任务并发策略:

- 同一个任务,只能由一个线程执行,不可以同时被多个线程执行
- id唯一,已经存在的,不可以再插入
- params唯一,已经存在的,不可以再插入

### 任务重启策略:

- 成功的不可以重启
- (#失败的不可以重启)
- 处理中的不可以重启
- 丢失的可以重启

### 任务重试策略:

- 等时重试n次;
- 指数间隔重试n次;
- 前3次等时后指数间隔一共重试n次;
- 无限重试;
- 首日重试n次，次日重试n次;

### 任务状态:

- success
- fail
- failretry
- pause
- pausequit

## 第四章　功能列表
### sdk功能列表
- 生产任务
- 消费任务
- 暂停（挂起）任务
- 恢复任务
- 移除任务
- 取消任务

### 管理平台功能列表
- 添加（补充）丢失任务
- 暂停（挂起）任务
- 重启任务
- 取消任务
- 查看任务列表
- 查看任务详情
- 修改任务执行状态
- 修改子任务执行状态

## 第五章 任务生产

## 第六章 任务消费

## 第七章 任务清理

- T日清理T-3日之前的任务
- 状态为成功，失败，挂起取消的任务，是最终状态，从redis删除: t_task, t_child_task
- 在任务管理系统实现

## 第八章 任务管理系统

- 查看历史任务信息
- 查看正在执行任务信息
- 增加、批量增加任务
