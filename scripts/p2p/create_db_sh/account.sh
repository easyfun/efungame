#!/bin/bash
#################################################################
#################################################################
password=123456

m=0
n=0

#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "


#zyxr 基础库
db=account
$mysql_exec -e "drop database ${db}"
$mysql_exec -e "create database ${db}"
$mysql_exec $db -e "create table t_id_config
(
  id int(11) NOT NULL COMMENT '用户ID类型',
  id_index text NOT NULL COMMENT '用户分配ID',
  nr_peralloc int(11) NOT NULL COMMENT '预留id段',
  last_update_time datetime NOT NULL COMMENT '日期时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

$mysql_exec $db -e "create table t_increase_id_config
(
  id int(11) NOT NULL COMMENT '用户ID类型',
  id_index mediumtext NOT NULL COMMENT '用户分配ID',
  nr_peralloc int(11) NOT NULL COMMENT '预留id段',
  last_update_time datetime NOT NULL COMMENT '日期时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#建立id server
for i in {0..25}
do
$mysql_exec $db -e "insert into  t_id_config 
(id,id_index,nr_peralloc,last_update_time)	   
 values 
($i,0,100,now());"
done

update t_id_config set nr_peralloc=5, id_index=1  where id=24;

#建立id server
for i in {0..5}
do
$mysql_exec $db -e "insert into  t_increase_id_config 
(id,id_index,nr_peralloc,last_update_time)	   
 values 
($i,0,100,now());"
done


for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	
	# 用户账户表
	$mysql_exec $db -e "create table t_user_account_${m}${n}
	(
	   acc_id             bigint not null default 0 comment '账户ID',
	   uid                bigint not null default 0 comment '用户UID',
	   user_name          varchar(64) not null default '' comment '用户名',
	   type               int not null default 0 comment '账户类型 1. 易宝支付 2.富友支付 3. 其他支付平台',
	   balance            bigint not null default 0 comment '余额',
	   frozen             bigint not null default 0 comment '冻结金额',
	   state              tinyint not null default 0 comment '状态：1-正常，2-冻结，3-其他异常',
	   parent             bigint not null default 0 comment '父账户ID',
	   deposit_amt        bigint not null default 0 comment '最近充值金额',
	   update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '最近充值时间',
	   primary key (acc_id),
	   unique key i_type_uid (type, uid)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

	# 用户账户流水表
	$mysql_exec $db -e "create table t_user_account_flow_${m}${n}
	(
	   flow_id            bigint not null default 0 comment '流水ID',
	   acc_id             bigint not null default 0 comment '账户ID',
	   uid                bigint not null default 0 comment '用户ID',
	   #20160829为了幂等的时候查流水  
	   type               int not null default 0 comment '账户类型 1. 易宝支付 2.富友支付 3. 其他支付平台',
	   bus_type           tinyint not null default 0 comment '业务类型：1-充值，2-提现，3-投资，4-缴费，5-放款，6-结算',
	   subbus_type        tinyint not null default 0 comment '子业务类型：1-gps押金，2-咨询服务费, ........',
	   flow_type          tinyint not null default 0 comment '流水类型：1-散标流水，2-理财计划流水',
	   operation          int not null default 0 comment '操作：1-增加余额，2-减少余额，3-冻结，4-解冻',
	   amount             bigint not null default 0 comment '变动金额',
	   balance            bigint not null default 0 comment '操作后余额',
	   frozen             bigint not null default 0 comment '改变之后的冻结金额',
	   peer_acc_id        bigint not null default 0 comment '交易对方账户ID',
	   peer_uid           bigint not null default 0 comment '对方的用户id',
	   trans_id           bigint not null default 0 comment '导致该流水的业务交易ID',
	   loan_id            bigint not null default 0 comment '标的ID',
	   repayment_id       bigint not null default 0 comment '还款计划ID',
	   remark             varchar(500) not null default '' comment '描述',
	   create_index       bigint not null auto_increment comment '创建记录的自增字段',
	   freeze_id          bigint not null default 0 comment '冻结单ID',
	   service_name       varchar(16) not null default '' comment '调用者服务名称',
	   deposit_amt        bigint not null default 0 comment '最近充值金额',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '最近充值时间',
	   primary key (flow_id),
	   unique key i_btype_operation_tranid (bus_type, operation, trans_id),
	   key i_create_index (create_index)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
done




# 资金账户表
$mysql_exec $db -e "create table t_biz_account
(
   acc_id             bigint not null default 0 comment '账户ID',
   type               int not null default 0 comment '账户类型 1. 易宝支付 2.富友支付 3. 其他支付平台',
   balance            bigint not null default 0 comment '余额',
   frozen             bigint not null default 0 comment '冻结金额',
   state              tinyint not null default 0 comment '状态：1-正常，2-冻结，3-其他异常',
   parent             bigint not null default 0 comment '父账户',
   deposit_amt        bigint not null default 0 comment '最近充值金额',
   update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '最近充值时间',
   primary key (acc_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	# 资金账户流水表
	$mysql_exec $db -e "create table t_biz_account_flow_${m}${n}
	(
	   flow_id            bigint not null default 0 comment '流水ID',
	   bus_type           tinyint not null default 0 comment '业务类型：待定',
	   operation          int not null default 0 comment '操作：1-增加余额，2-减少余额，3-转出冻结，4-转入冻结',
	   acc_id             bigint not null default 0 comment '账户ID',
	   type               int not null default 0 comment '账户类型 1. 易宝支付 2.富友支付 3. 其他支付平台',
	   amount             bigint not null default 0 comment '变动金额',
	   peer_uid           bigint not null default 0 comment '对方用户id',
	   remark             varchar(500) not null default '' comment '描述',
	   peer_acc_id        bigint not null default 0 comment '交易对方账户ID',
	   trans_id           bigint not null default 0 comment '导致该流水的业务交易ID',
	   loan_id            bigint not null default 0 comment '标的ID',
	   repayment_id       bigint not null default 0 comment '还款计划ID',
	   balance            bigint not null default 0 comment '操作后余额',
	   frozen             bigint not null default 0 comment '改变之后的冻结金额',
	   create_index       bigint not null auto_increment comment '创建自增字段',
	   freeze_id          bigint not null default 0 comment '冻结单ID',
	   service_name       varchar(16) not null default '' comment '调用者服务名称',
	   deposit_amt        bigint not null default 0 comment '最近充值金额',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '最近充值时间',
	   primary key (flow_id),
	   unique key i_btype_operation_transid (bus_type, operation, trans_id),
	   key i_create_index (create_index)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

        # 冻结单表
	$mysql_exec $db -e "create table t_freeze_order_${m}${n}
	(
	   order_id           bigint not null default 0 comment '冻结单号，调用者传入',
	   acc_id             bigint not null default 0 comment '冻结账号id',
	   uid           bigint not null default 0 comment '冻结用户id',
	   amount             bigint not null default 0 comment '冻结总金额',
	   bus_type           int not null default 0 comment '业务类型',
	   unfrozen_amount    bigint not null default 0 comment '已解冻金额',
	   loan_id            bigint not null default 0 comment '标的ID',
	   state              int not null default 0 comment '状态：1-冻结，2-部分解冻，3-已全部解冻',
	   remark             varchar(64) not null default '' comment '描述',
	   update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (order_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
done	

# 提现黑名单表
$mysql_exec $db -e "create table t_user_account_withdrawblacklist
(
	acc_id             bigint not null default 0 comment '账户ID',
	uid                bigint not null default 0 comment '用户UID',
	user_name          varchar(64) not null default '' comment '用户名',
	type               int not null default 0 comment '账户类型 1. 易宝支付 2.富友支付 3. 其他支付平台',
	balance            bigint not null default 0 comment '余额',
	frozen             bigint not null default 0 comment '冻结金额',
	state              tinyint not null default 0 comment '状态：1-正常，2-冻结，3-其他异常',
	parent             bigint not null default 0 comment '父账户ID',
	deposit_amt        bigint not null default 0 comment '最近充值金额',
	withdraw_status	  tinyint not null default 0 comment '状态：1-正常，0-冻结，2-没有开户',
	update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	deposit_time        datetime not null default '0000-00-00 00:00:00' comment '最近充值时间',
	primary key (acc_id),
	unique key i_type_uid (type, uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


