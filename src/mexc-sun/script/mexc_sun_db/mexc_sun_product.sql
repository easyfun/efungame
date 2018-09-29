-- 第一期不上流水表

use `mexc_sun`;


create table `t_market` (
	`market_id` bigint unsigned not null comment '市场id',
	`market_name` varchar(16) not null comment '市场名称',
	`market_english_name` varchar(80) not null comment '市场英文名称',
	`market_chinese_name` varchar(80) default null comment '市场中文名称',
	`main_coin_id` bigint unsigned not null comment '主币id',
	`main_coin_name` varchar(16) not null comment '主币名称',
	`order_number` int not null comment '排序序号',
	`market_status` tinyint unsigned not null comment '市场状态',
	`version` int unsigned not null default '0' comment '版本号',
	`create_time` datetime not null comment '创建时间',
  	`update_time` datetime not null comment '更新时间',
	primary key (`market_id`),
	unique key `uk_market_name` (`market_name`)
) engine=innodb default charset=utf8 comment='市场信息表';



-- create table `t_market_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`market_id` bigint unsigned not null comment '变更批次号',
-- 	`market_name` varchar(16) not null comment '市场名称',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	-- `login_id` bigint unsigned not null comment '登录id',
-- 	-- `login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`)
-- 	-- key k_market_id_change_batch_id_field_name(`market_id`, `batch_id`, `field_name`),
-- 	-- key k_market_name_change_batch_id_field_name(`market_name`, `batch_id`, `field_name`),
-- 	-- key k_create_time (`create_time`)
-- ) engine=innodb default charset=utf8 comment='市场信息变更流水表';



create table `t_coin` (
	`coin_id` bigint unsigned not null comment '币id',
	`coin_name` varchar(16) not null comment '币名称',
	`coin_english_name` varchar(80) not null comment '币英文名称',
	`coin_chinese_name` varchar(80) not null comment '币中文名称',
	`coin_token` varchar(16) not null comment '币token',
	`order_number` int not null comment '排序序号',
	`is_main_coin` tinyint unsigned not null comment '是否是主币:0不是主币; 1是主币',
	`main_coin_id` bigint unsigned not null comment '对应主币id',
	`main_coin_name` varchar(16) not null comment '对应主币名称',
	`coin_statu` tinyint unsigned not null comment '币状态',
	`version` int unsigned not null default '0' comment '版本号',
	`is_cash` tinyint unsigned not null comment '是否可提现:0不可以; 1可以',
	`is_recharge` tinyint unsigned not null comment '是否可充值:0不可以; 1可以',
	`is_trade` tinyint unsigned not null comment '是否可交易:0不可以; 1可以',
	`pre_publish_time` datetime default null comment '预发布时间',
	`publish_time` datetime default null comment '正式发布时间',
	`contract_address` varchar(80) DEFAULT NULL COMMENT '合约地址',
	`coin_scale` int DEFAULT NULL COMMENT '币小数位位数',
	`recharge_confirmations` int DEFAULT '3' COMMENT '充值区块确认数',
	`cash_confirmations` int DEFAULT '15' COMMENT '提现区块确认数',
	`hot_to_cold_threshold` decimal(20,8) not null COMMENT '热转冷阈值',
	-- TODO: btc阈值名称修改
	`btc_threshold_1` decimal(20,8) not null COMMENT 'btc热转冷阈值1',
	`btc_threshold_2` decimal(20,8) not null COMMENT 'btc热转冷阈值2',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	`coin_icon` varchar(160) default null comment '币图标链接',
	primary key (`coin_id`),
	unique key `uk_coin_english_name` (`coin_english_name`)
) engine=innodb default charset=utf8 comment='币种信息表';



-- create table `t_coin_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`coin_id` bigint unsigned not null comment '币id',
-- 	`coin_name` varchar(16) not null comment '币名称',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	-- `login_id` bigint unsigned not null comment '登录id',
-- 	-- `login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	-- key k_coin_id_change_batch_id_field_name(`coin_id`, `batch_id`, `field_name`),
-- 	-- key k_coin_name_change_batch_id_field_name(`coin_name`, `batch_id`, `field_name`),
-- 	key `k_create_time` (`create_time`)
-- ) engine=innodb default charset=utf8 comment='币种信息变更流水表';



create table `t_coin_fee` (
	`coin_id` bigint unsigned not null comment '币id',
	`coin_name` varchar(16) not null comment '币名称',
	`buy_rate` decimal(20,8) not null comment '买单手续费率',
	`sell_rate` decimal(20,8) not null comment '卖单手续费率',
	`put_up_rate` decimal(20,8) default '0.0' comment '挂单手续费',
	`cash_rate` decimal(20,8) not null comment '提现手续费率',
	`min_cash_amount` decimal(20,8) not null comment '最小提现额',
	`max_cash_amount` decimal(20,8) not null comment '最大提现额',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`coin_id`),
	unique key `uk_coin_name` (`coin_name`)
) engine=innodb default charset=utf8 comment='币种费用信息表';




-- create table `t_coin_fee_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`coin_id` bigint unsigned not null comment '币id',
-- 	`coin_name` varchar(16) not null comment '币名称',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	-- `login_id` bigint unsigned not null comment '登录id',
-- 	-- `login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	-- key k_coin_id_change_batch_id_field_name(`coin_id`, `batch_id`, `field_name`),
-- 	-- key k_coin_name_change_batch_id_field_name(`coin_name`, `batch_id`, `field_name`),
-- 	key `k_create_time` (`create_time`)
-- ) engine=innodb default charset=utf8 comment='币种费用信息变更流水表';



create table `t_market_coin` (
	`market_coin_id` bigint unsigned not null comment '交易对id',
	`market_coin_name` varchar(32) not null comment '交易对名称',
	`market_id` bigint unsigned not null comment '市场id',
	`market_name` varchar(16) not null comment '市场名称',
	`main_coin_id` bigint unsigned not null comment '对应主币id',
	`main_coin_name` varchar(16) not null comment '对应主币名称',
	`coin_id` bigint unsigned not null comment '币id',
	`coin_name` varchar(16) not null comment '币名称',
	`coin_english_name` varchar(80) not null comment '币英文名称',
	`max_trade_amount` decimal(20, 8) not null comment '最大交易限额',
	`min_trade_amount` decimal(20, 8) not null comment '最小交易限额',
	`market_coin_status` tinyint unsigned not null comment '市场币种交易对状态',
	`version` int unsigned not null default '0' comment '版本号',
	`price_show_scale` int not null comment '价格显示小数位数',
	`number_show_scale` int not null comment '数量显示小数位数',
	`amount_show_scale` int not null comment '金额显示小数位数',
	`dollar_valuation_show_scale` int not null comment '美元估值显示小数位数',
	`rmb_valuation_show_scale` int not null comment '人民币估值显示小数位数',
	`turnover_24h_show_scale` int not null comment '24小时成交额显示小数位数', 
	`coin_icon` varchar(160) default null comment '币图标链接',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`coin_id`),
	unique key `uk_market_name_coin_english_name` (`market_name`, `coin_english_name`)
) engine=innodb default charset=utf8 comment='市场币种交易对信息表';



-- create table `t_market_coin_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`market_coin_id` bigint unsigned not null comment 'id',
-- 	`market_coin_name` varchar(32) not null comment '交易对名称',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	-- `login_id` bigint unsigned not null comment '登录id',
-- 	-- `login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	-- key k_coin_id_change_batch_id_field_name(`coin_id`, `batch_id`, `field_name`),
-- 	-- key k_coin_name_change_batch_id_field_name(`coin_name`, `batch_id`, `field_name`),
-- 	key `k_create_time` (`create_time`)
-- ) engine=innodb default charset=utf8 comment='币种信息变更流水表';




create table `t_wallet` (
	`wallet_id` bigint unsigned not null comment '钱包id',
	`wallet_name` varchar(64) not null comment '钱包名称',
	`wallet_mode` varchar(32) not null comment '钱包类型:ETH, BTC, USDT',
	`wallet_status` tinyint unsigned not null comment '钱包状态:0离线; 1在线',
	`version` int unsigned not null default '0' comment '版本号',
	`cold_address` varchar(80) not null comment '冷钱包地址',
	`hot_address` varchar(80) not null comment '热钱包地址',
	`hot_pwd` varchar(100) default null comment '热钱包密码',
	`hot_eth_address_pwd` varchar(100) default null comment 'eth地址密码',
	`hot_eth_address_iv` varchar(32) default null comment 'eth地址iv向量',
	`hot_eth_address_phrase` varchar(80) default null comment 'eth地址短语',
	`hot_eth_address_file_path` varchar(100) default null comment 'eth地址文件地址',
	`hot_ip` varchar(32) not null comment '热钱包ip',
	`hot_port` varchar(32) not null comment '热钱包地址',
	`hot_rpc_user` varchar(32) default null comment '热钱包rpc账户',
	`hot_rpc_pwd` varchar(100) default null comment '热钱包rpc密码',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`wallet_id`)
) engine=innodb default charset=utf8 comment='钱包信息表';



-- create table `t_wallet_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`wallet_id` bigint unsigned not null comment '钱包id',
-- 	`wallet_name` varchar(64) not null comment '钱包名称',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	-- `login_id` bigint unsigned not null comment '登录id',
-- 	-- `login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	-- key k_coin_id_change_batch_id_field_name(`coin_id`, `batch_id`, `field_name`),
-- 	-- key k_coin_name_change_batch_id_field_name(`coin_name`, `batch_id`, `field_name`),
-- 	key `k_create_time` (`create_time`)
-- ) engine=innodb default charset=utf8 comment='币种信息变更流水表';

