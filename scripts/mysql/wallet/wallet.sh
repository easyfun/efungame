#!/bin/bash
password=easyfun
mysql_exec="mysql -uroot -p$password"
db=task

$mysql_exec -e "use mexc;"

#$mysql_exec $db -e "create table t_mexc_recharge_sync_task (
#  id                bigint unsigned not null auto_increment comment 'id',
#  block_id          bigint not null comment '区块id',
#  coin_market_name  varchar(8) not null comment '币市场名称',
#  status            int not null default '0' comment '处理状态:0-未知;1-创建;2-成功;99-失败',
#  transaction_count int not null default '0' comment '事务条数',
#  recharge_count    int not null default '0' comment '充值记录数',
#  create_time        datetime not null comment '创建时间',
#  update_time        datetime not null comment '更新时间',
#  primary key (id),
#  unique key uk_block_id(block_id)
#) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '充值任务信息表';"

$mysql_exec $db -e "CREATE TABLE t_mexc_btc_recharge_region_sync_task_detail (
  id                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  coin_market_name  VARCHAR(8) NOT NULL COMMENT '币名称',
  batch_id          BIGINT DEFAULT NULL COMMENT '区块id',
  `counts`             BIGINT NOT NULL DEFAULT '1' COMMENT '条数',
  `froms`              BIGINT NOT NULL DEFAULT '1' COMMENT '开始位置',
  STATUS            INT NOT NULL DEFAULT '0' COMMENT '处理状态:0-未知;1-创建;2-确认中;98-成功;99-失败',
  fail_reason       VARCHAR(100) DEFAULT NULL COMMENT '失败原因',
  txid              VARCHAR(72) DEFAULT NULL COMMENT 'txid',
  category  VARCHAR(32) DEFAULT NULL,
  address   VARCHAR(64) DEFAULT NULL,
  account   VARCHAR(64) DEFAULT NULL,
  amount    VARCHAR(64) DEFAULT NULL,
  create_time        DATETIME NOT NULL COMMENT '创建时间',
  update_time        DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  key idx_txid(txid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '充值区间同步任务表';"


$mysql_exec $db -e "create table t_mexc_eth_recharge_sync_task (
  id                bigint unsigned not null auto_increment comment 'id',
  block_number          bigint not null comment '区块编号',
  coin_market_name  varchar(8) not null comment '币市场名称',
  status            int not null default '0' comment '处理状态:0-未知;1-创建;98-成功;99-失败',
  transaction_count bigint not null default '0' comment '交易条数',
  create_time        datetime not null comment '创建时间',
  update_time        datetime not null comment '更新时间',
  primary key (id),
  unique key uk_block_number(block_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'eth类充值任务表';"

$mysql_exec $db -e "CREATE TABLE t_mexc_eth_recharge_sync_task_detail (
  id                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  coin_market_name  VARCHAR(8) NOT NULL COMMENT '币市场名称',
  coin_name         VARCHAR(8) DEFAULT NULL COMMENT '币名称',
  block_number      BIGINT DEFAULT NULL COMMENT '区块编号',
  transaction_index      BIGINT DEFAULT NULL COMMENT '交易编号',
  STATUS            INT NOT NULL DEFAULT '0' COMMENT '处理状态:0-未知;1-创建;2-确认中;98-成功;99-失败',
  fail_reason       VARCHAR(100) DEFAULT NULL COMMENT '失败原因',
  transaction_hash              VARCHAR(72) DEFAULT NULL COMMENT 'transaction_hash',
  from_address  VARCHAR(82) DEFAULT NULL,
  to_address   VARCHAR(82) DEFAULT NULL,
  amount    VARCHAR(64) DEFAULT NULL,
  create_time        DATETIME NOT NULL COMMENT '创建时间',
  update_time        DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  key idx_txid(transaction_hash),
  unique key uk_block_number_transaction_index(block_number, transaction_index)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'eth类充值同步任务明细表';"

# 充值表增加唯一性约束，防止多次增加用户资产
#alter table t_mexc_asset_recharge add unique key uk_tx_hash(tx_hash);



