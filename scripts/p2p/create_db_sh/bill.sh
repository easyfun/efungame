db="trustee"
mysql_exec="mysql -uroot -p123456"
m=0
n=0

    # 中业兴融账单
    $mysql_exec $db -e "CREATE TABLE `t_bill_zyxr` (
	  `order_id` bigint(20) not null default '0' comment '订单号',
	  `uid` bigint(20) not null default '0' comment '用户ID',
	  `mobile` varchar(11) not null default '' comment '手机号码',
	  `real_name` varchar(30) not null default '' comment '用户真实姓名',
	  `amount` bigint(20) not null default '0' comment '交易金额',
	  `to_uid` bigint(20) not null default '0' comment '交易对方ID',
	  `to_mobile`  varchar(11) not null default '' comment '交易对方手机号码',
	  `channel` int(11) not null default '0' comment '交易渠道0-易宝，1-富友',
	  `bus_type` int(11) not null default '0' comment '业务类型',
	  `state` int(11) not null default '0' comment '交易状态',
	  `trade_time` datetime not null default '0000-00-00 00:00:00' comment '交易时间',
	  `remark` varchar(64) not null default '' comment '备注',
	  PRIMARY KEY (`order_id`)
	) ENGINE=InnoDB default CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='中业兴融账单';"
	
	
	# 第三方存管账单
	$mysql_exec $db -e "CREATE TABLE `t_bill_trustee` (
	  `order_id` bigint(20) not null default '0' comment '订单号',
	  `uid` bigint(20) not null default '0' comment '用户ID',
	  `mobile` varchar(11) not null default '' comment '手机号码',
	  `real_name` varchar(30) not null default '' comment '用户真实姓名',
	  `amount` bigint(20) not null default '0' comment '交易金额',
	  `to_uid` bigint(20) not null default '0' comment '交易对方ID',
	  `to_mobile`  varchar(11) not null default '' comment '交易对方手机号码',
	  `channel` int(11) not null default '0' comment '交易渠道0-易宝，1-富友',
	  `bus_type` int(11) not null default '0' comment '业务类型',
	  `state` int(11) not null default '0' comment '交易状态',
	  `trade_time` datetime not null default '0000-00-00 00:00:00' comment '交易时间',
	  `remark` varchar(64) not null default '' comment '备注',
	  PRIMARY KEY (`order_id`)
	) ENGINE=InnoDB default CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='第三方存管账单';"
	
	
	# 对账结果表
	$mysql_exec $db -e "CREATE TABLE `t_bill_check` (
	  `order_id` bigint(20) not null default '0' comment '订单号',
	  `uid` bigint(20) not null default '0' comment '用户ID',
	  `mobile` varchar(11) not null default '' comment '手机号码',
	  `real_name` varchar(30) not null default '' comment '用户真实姓名',
	  `amount` bigint(20) not null default '0' comment '交易金额',
	  `peer_uid` bigint(20) not null default '0' comment '交易对方ID',
	  `peer_mobile`  varchar(11) not null default '' comment '交易对方手机号码',
	  `channel` int(11) not null default '0' comment '交易渠道0-易宝，1-富友',
	  `bus_type` int(11) not null default '0' comment '业务类型',
	  `state` int(11) not null default '0' comment '交易状态',
	  `trade_time` datetime default NULL comment '交易时间',
	  `remark` varchar(64) not null default '' comment '备注',
	  
	  `f_order_id` bigint(20) not null default '0' comment '订单号',
	  `f_uid` bigint(20) not null default '0' comment '用户ID',
	  `mobile` varchar(11) not null default '' comment '手机号码',
	  `real_name` varchar(30) not null default '' comment '用户真实姓名',
	  `amount` bigint(20) not null default '0' comment '交易金额',
	  `peer_uid` bigint(20) not null default '0' comment '交易对方ID',
	  `peer_mobile`  varchar(11) not null default '' comment '交易对方手机号码',
	  `channel` int(11) not null default '0' comment '交易渠道0-易宝，1-富友',
	  `bus_type` int(11) not null default '0' comment '业务类型',
	  `state` int(11) not null default '0' comment '交易状态',
	  `trade_time` datetime default NULL comment '交易时间',
	  `remark` varchar(64) not null default '' comment '备注',
	  PRIMARY KEY (`order_id`)
	) ENGINE=InnoDB default CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='对账结果表';"