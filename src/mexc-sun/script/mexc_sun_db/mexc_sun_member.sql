use `mexc_sun`;


CREATE TABLE `t_platform_email` (
	`email_id` bigint unsigned not null comment '邮箱id',
	`username` varchar(128) not null comment '邮箱账号',
	`password` varchar(100) not null comment '邮箱密码',
	`smtp_host` varchar(64) not null 'smtp主机',
	`smtp_port` int not null comment 'smtp端口',
	
	`email_mode` tinyint unsigned not null COMMENT '邮箱类型: 1-gmail; 2-qqmail;3-qq企业',
	`email_function` tinyint unsigned not null comment '邮箱功能: 0-注册',
	`email_group` varchar(32) not null '邮箱组',
	`email_status` tinyint unsigned not null COMMENT '邮箱状态: 0-不启用; 1-启用; 2-冻结',
	`send_interval_seconds` int unsigned not null comment '发送间隔秒数',
	
	`success_times` int not null comment '总成功次数',
	`fail_times` int not null comment '总失败次数',
	`timeout_times` int not null comment '总超时次数',
	
	`freeze_continuous_failure_times` int not null comment '冻结连续失败次数',
	`freeze_last_1min_failure_times` int not null comment '冻结最近1分钟失败次数',
	`freeze_times` int unsigned not null comment '总冻结次数',
	`freeze_start_time` datetime not null comment '冻结开始时间',
	`freeze_end_time` datetime not null comment '冻结结束时间',

	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
  PRIMARY KEY (`email_id`),
  unique key `uk_username_mode_function_group`(`username`, `email_mode`, `email_function`, `email_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台邮箱';


CREATE TABLE `t_platform_email_daily_stats` (
	`stats_id` bigint unsigned not null comment '统计id',
	`day_date` varchar(16) not null comment '当日日期:YYYY-MM-DD',

	`email_id` bigint unsigned not null comment '邮箱id',
	`username` varchar(128) not null comment '邮箱账号',

	`day_success_times` int not null comment '当日成功次数',
	`day_fail_times` int not null comment '当日失败次数',
	`day_timeout_times` int not null comment '当日超时次数',
	`day_freeze_times` int unsigned not null comment '当日冻结次数',

	`last_success_time` datetime not null comment '最近成功时间',
	`last_fail_time` datetime not null comment '最近失败时间',
	`continuous_failure_times` int not null comment '连续失败次数',
	`last_1min_failure_times` int not null comment '最近1分钟失败次数',

	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
  PRIMARY KEY (`stats_id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台邮箱日统计数据';


CREATE TABLE `t_platform_email_freeze_flow` (
	`flow_id` bigint unsigned not null comment '流水id',
	`flow_mode` tinyint unsigned not null comment '流水类型:0-冻结,1-解冻',

	`email_id` bigint unsigned not null comment '邮箱id',
	`username` varchar(128) not null comment '邮箱账号',

	`last_fail_time` datetime default null comment '最近失败时间',
	`continuous_failure_times` int default null comment '连续失败次数',
	`last_1min_failure_times` int default null comment '最近1分钟失败次数',
	`freeze_start_time` datetime default null comment '冻结开始时间',
	`freeze_end_time` datetime default null comment '冻结结束时间',
	`unfreeze_time` datetime default null comment '解冻时间',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
  PRIMARY KEY (`stats_id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台邮箱冻结流水表';


CREATE TABLE `t_platform_email_send` (
	`send_id` bigint NOT NULL comment '发送id',
	`email_id` bigint unsigned not null comment '邮箱id',
	`username` varchar(128) not null comment '邮箱账号',
	`to_address` varchar(128) not null comment '接收方地址',
	`content` varchar(1240) default null comment '邮件内容',
	`send_status` tinyint unsigned not null COMMENT '发送状态: 98-成功; 99-失败',
	`result` tinyint unsigned not null comment '结果: 0-处理中(未知); 98-成功; 99-失败',
	`fail_code` varchar(16) not null comment '失败码',
	`fail_reason` varchar(256) not null comment '失败原因',
	`apply_time` datetime not null comment '申请时间',
	`finish_time` datetime not null comment '完成时间',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	PRIMARY KEY (`send_id`),
	KEY k_to_address (`to_address`)
) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8 COMMENT='平台邮件发送记录表';


create table `t_email_register_apply` (
	`apply_id` bigint unsigned not null comment '申请id',
	`user_id` bigint unsigned default null comment '会员id',
	`member_pwd` varchar(64) not null comment '会员密码',
	`member_pwd_salt` bigint unsigned not null comment '会员密码salt',
	`trade_pwd` varchar(64) not null comment '交易密码',
	`trade_pwd_salt` bigint unsigned not null comment '交易密码salt',
	`email` varchar(80) default null comment '邮箱',
	#`verification_code` varchar(10) not null comment '验证码',
	`email_verification_code` varchar(10) not null comment '邮箱验证码',
	`register_status` tinyint unsigned not null comment '注册状态:0-申请; 98-成功; 99-失败',
	`result` tinyint unsigned not null comment '结果: 0-处理中(未知); 98-成功; 99-失败',
	`fail_code` varchar(16) not null comment '失败码',
	`fail_reason` varchar(256) not null comment '失败原因',
	`register_time` datetime not null comment '注册时间',
	`register_ip` varchar(24) not null comment '注册ip',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`apply_id`),
	key k_user_id(`user_id`),
	key k_email(`email`),
	key k_register_time (`register_time`)
) engine=innodb default charset=utf8 comment='email注册申请表';


create table `t_user_local_auth` (
	`user_id` bigint unsigned not null comment '会员id',
	`user_pwd` varchar(64) not null comment '会员密码',
	#`trade_pwd` varchar(64) not null comment '交易密码',
	`email` varchar(80) default null comment '邮箱',
	`mobile` varchar(16) default null comment '手机号',
	`register_time` datetime not null comment '注册时间',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`user_id`)
) engine=innodb default charset=utf8 comment='会员认证信息表';


create table `t_user` (
	`user_id` bigint unsigned not null comment '会员id',
	`user_mode` tinyint unsigned not null comment '会员类型:1普通;2-机构;3-平台',
	`auth_level` tinyint unsigned not null comment '认证级别',
	`email` varchar(80) default null comment '邮箱',
	`email_status` tinyint unsigned not null comment '邮箱状态',
	`email_active_time` datetime default null comment '邮箱激活时间',

	`mobile` varchar(16) default null comment '手机号',
	`mobile_status` tinyint unsigned not null comment '手机号状态',
	`mobile_active_time` datetime default null comment '手机号激活时间',
	`member_status` tinyint unsigned not null comment '会员状态',
	`version` int unsigned not null default '0' comment '版本号',

	`register_time` datetime not null comment '注册时间',
	`register_ip` varchar(24) not null comment '注册ip',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`user_id`),
	unique key uk_email(`email`),
	unique key uk_mobile(`mobile`),
	key k_email_active_time (`email_active_time`),
	key k_mobile_active_time (`mobile_active_time`),
	key k_register_time (`register_time`)
) engine=innodb default charset=utf8 comment='会员信息表';



create table `t_user_attribute` (
	`attribute_id` bigint unsigned not null comment '属性id',
	`user_id` bigint unsigned not null comment '会员id',
	`field_name` varchar(64) not null comment '属性名称:head_icon头像链接',
	`field_value` varchar(160) default null comment '属性值',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`attribute_id`),
	key k_user_id_field_name(`user_id`, `field_name`)
) engine=innodb default charset=utf8 comment='会员属性表';



-- create table `t_member_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`user_id` bigint unsigned not null comment '会员id',
-- 	`batch_id` bigint unsigned not null comment '变更批次号',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	`login_id` bigint unsigned not null comment '登录id',
-- 	`login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	key k_user_id_change_batch_id_field_name(`user_id`, `batch_id`, `field_name`),
-- 	key k_create_time (`create_time`)
-- ) engine=innodb default charset=utf8 comment='会员信息变更流水表';



create table `t_user_real_auth` (
	`user_id` bigint unsigned not null comment '会员id',
	`auth_level` tinyint unsigned not null comment '认证级别',
	`card_mode` tinyint unsigned default null comment '证件类型',
	`card_no` varchar(100) default null comment '证件编号',
	`area_mode` int unsigned default null comment '地区类型：1-中华人民共和国，2-其它国家或地区',
	`area_name` varchar(160) default null comment '地区名称',
	`real_name` varchar(100) default null comment '真实姓名',
	`card_image_a` varchar(160) default null comment '证件a面图片',
	`card_image_b` varchar(160) default null comment '证件b面图片',
	`card_image_hand_a` varchar(160) default null comment '手持证件a面图片',
	`card_image_hand_b` varchar(160) default null comment '手持证件b面图片',
	`card_auth_statu` tinyint unsigned not null comment '证件认证状态:0已认证;1未认证;2审核中',
	`card_auth_time` datetime default null comment '证件认证时间',
	-- TODO: 需要梳理
	`google_code` varchar(160) default null comment '谷歌认证码',
	`google_auth_status` tinyint unsigned not null comment '谷歌认证状态:0-已认证;1未认证;2审核中',
	`google_auth_time` datetime default null comment '谷歌认证时间',
	`version` int unsigned not null default '0' comment '版本号',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`user_id`),
	key k_card_mode_card_no (`card_mode`, `card_no`),
	key k_card_auth_time (`card_auth_time`)
	-- key k_google_auth_time (`google_auth_time`)
) engine=innodb default charset=utf8 comment='会员实名认证信息表';



-- create table `t_user_auth_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`user_id` bigint unsigned not null comment '会员id',
-- 	`batch_id` bigint unsigned not null comment '变更批次号',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	`login_id` bigint unsigned not null comment '登录id',
-- 	`login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	key k_user_id_change_batch_id_field_name(`user_id`, `batch_id`, `field_name`),
-- 	key k_create_time (`create_time`)
-- ) engine=innodb default charset=utf8 comment='会员认证信息变更流水表';



create table `t_user_login_flow` (
	`login_id` bigint unsigned not null comment '登录id',
	`user_id` bigint unsigned not null comment '会员id',
	`flow_mode` tinyint unsigned not null comment '变更类型:0-登录; 1-注销',
	`login_ip` varchar(32) not null comment '登录ip',
	`login_status` tinyint unsigned not null comment '登录状态: 98-成功; 99-失败',
	`fail_code` varchar(16) not null comment '失败码',
	`fail_reason` varchar(100) not null comment '失败原因',
	`login_time` datetime not null comment '登录时间',
	`logout_time` datetime default null comment '注销时间',
	`region_id` varchar(32) default null comment '区域id',
	`country_id` varchar(32) default null comment '国家id',
	`city_id` varchar(32) default null comment '城市id',
	`address` varchar(256) default null comment '地址',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`login_id`),
	key k_user_id_login_ip(`user_id`, `login_ip`),
	key k_login_time (`login_time`)
) engine=innodb default charset=utf8 comment='会员登录流水表';



create table `t_user_asset` (
	`asset_id` bigint unsigned not null comment '资产id',
	`user_id` bigint unsigned not null comment '会员id',
	`coin_id` bigint unsigned not null comment '币id',
	`coin_name` varchar(16) not null comment '币名称',
	`coin_token` varchar(16) not null comment '币token',
	`is_cash` tinyint unsigned not null comment '是否可提现:0不可以; 1可以',
	`is_recharge` tinyint unsigned not null comment '是否可充值:0不可以; 1可以',
	`is_trade` tinyint unsigned not null comment '是否可交易:0不可以; 1可以',
	`total_amount` decimal(40,18) not null comment '总额: =可用余额+冻结余额',
	`balance_amount` decimal(40,18) not null comment '可用余额',
	`frozen_amount` decimal(40,18) not null  comment '冻结金额: =提现冻结金额+交易冻结金额',
	`cash_frozen_amount` decimal(40,18) not null  comment '提现冻结金额',
	`buy_frozen_amount` decimal(40,18) not null  comment '买入冻结金额',
	`sell_frozen_amount` decimal(40,18) not null  comment '卖出冻结金额',
	`asset_status` tinyint unsigned not null comment '资产状态：0-新建，1-正常，2-删除',
	`version` int unsigned not null default '0' comment '版本号',
	`wallet_id` varchar(255) default null comment '钱包id',
	`wallet_name` varchar(64) not null comment '钱包名称',
	`wallet_mode` varchar(32) not null comment '钱包类型:eth, btc, usdt',
	`wallet_address` varchar(96) default null comment '钱包地址',
	`eth_address_pwd` varchar(100) default null comment 'eth地址密码',
	`eth_address_iv` varchar(32) default null comment 'eth地址iv向量',
	`eth_address_phrase` varchar(80) default null comment 'eth地址短语',
	`eth_address_file_path` varchar(100) default null comment 'eth地址文件地址',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`asset_id`),
	key `k_user_id_coin_name` (`user_id`,`coin_name`),
	key `k_user_id_coin_id` (`user_id`, `coin_id`)
) engine=innodb default charset=utf8 comment='会员资产表';



create table `t_user_asset_amount_flow` (
	`flow_id` bigint unsigned not null comment '流水id',
	`asset_id` bigint unsigned not null comment '资产id',
	`user_id` bigint unsigned not null comment '会员id',
	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
	`coin_id` bigint unsigned not null comment '币id',
	`coin_name` varchar(16) not null comment '币名称',
	`coin_token` varchar(16) not null comment '币token',
	`change_mode` int unsigned not null comment '金额变动类型: 0-充值; 10-提现申请; 11-提现完成; 12-提现撤销; 20-买入申请; 21-买入撤单; 22-买入成交; 30-卖出申请; 31-卖出撤单; 32-卖出完成; 40-后台充值; 50-平台活动派送',
	`in_or_out` tinyint unsigned not null comment '增加or减少: 0-in; 1-out',
	`amount_mode` tinyint unsigned not null comment '变动金额类型: 0-可用; 1-提现冻结; 2-买入冻结; 3-卖出冻结',
	`change_amount` decimal(40,18) not null comment '变动金额',
	`src_main_id` bigint unsigned not null comment '来源主业务id',
	`src_slave_id` bigint unsigned default null comment '来源副业务id',
	`total_amount` decimal(40,18) not null comment '总额',
	`balance_amount` decimal(40,18) not null comment '可用余额',
	`frozen_amount` decimal(40,18) not null  comment '冻结金额',
	`cash_frozen_amount` decimal(40,18) not null  comment '提现冻结金额',
	`buy_frozen_amount` decimal(40,18) not null  comment '买入冻结金额',
	`sell_frozen_amount` decimal(40,18) not null  comment '卖出冻结金额',
	`version` int unsigned not null default '0' comment '版本号',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	primary key (`flow_id`),
	key `k_asset_id` (`asset_id`, `user_id`, `src_main_id`)
) engine=innodb default charset=utf8 comment='会员资产金额变更流水表';



-- create table `t_user_asset_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`user_id` bigint unsigned not null comment '会员id',
-- 	`batch_id` bigint unsigned not null comment '变更批次号',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	`login_id` bigint unsigned not null comment '登录id',
-- 	`login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	key k_user_id_change_batch_id_field_name(`user_id`, `batch_id`, `field_name`),
-- 	key k_create_time (`create_time`)
-- ) engine=innodb default charset=utf8 comment='会员资产信息变更流水表';



-- create table `t_address_pool` (
-- 	`address_id` bigint unsigned not null comment '地址id',
-- 	`address` varchar(80) not null comment '地址',
-- 	`address_name` varchar(64) not null comment '地址名称: eth, btc, usdt',
-- 	`wallet_id` bigint unsigned not null comment '钱包id',
-- 	`wallet_name` varchar(64) not null comment '钱包名称:parity, bitcoin',
-- 	`wallet_mode` varchar(32) not null comment '钱包类型:eth, btc, usdt',
-- 	`user_id` bigint unsigned default null comment '会员id',
-- 	`used_status` tinyint unsigned not null comment '是否使用:0未使用; 1已使用',
-- 	`version` int unsigned not null default '0' comment '版本号',
-- 	`eth_address_pwd` varchar(100) default null comment 'eth地址密码',
-- 	`eth_address_iv` varchar(32) default null comment 'eth地址iv向量',
-- 	`eth_address_phrase` varchar(80) default null comment 'eth地址短语',
-- 	`eth_address_file_path` varchar(100) default null comment 'eth地址文件地址',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`address`),
-- 	key `k_user_id` (`user_id`)
-- ) engine=innodb default charset=utf8 comment='币种信息变更流水表';



-- create table `t_address_pool_flow` (
-- 	`flow_id` bigint unsigned not null comment '流水id',
-- 	`address` varchar(80) not null comment '地址',
-- 	`address_name` varchar(64) not null comment '地址名称: eth, btc, usdt',
-- 	`flow_mode` tinyint unsigned not null comment '变更类型:0-创建; 1-修改',
-- 	`field_name` varchar(64) not null comment '字段名称',
-- 	`field_value` varchar(160) not null comment '字段值', 
-- 	`flow_status` tinyint unsigned not null comment '变更结果:0-成功,1-失败,2-处理中',
-- 	-- `login_id` bigint unsigned not null comment '登录id',
-- 	-- `login_ip` varchar(24) not null comment '登录ip',
-- 	`create_time` datetime not null comment '创建时间',
-- 	`update_time` datetime not null comment '更新时间',
-- 	primary key (`flow_id`),
-- 	key `k_create_time` (`create_time`)
-- ) engine=innodb default charset=utf8 comment='币种信息变更流水表';



