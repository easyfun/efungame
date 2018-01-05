#!/bin/bash
password=123456
mysql_exec="mysql -uroot -p$password"
db=financial_plan_match

$mysql_exec -e "create database $db;"

#待匹配理财计划，资产列表
$mysql_exec $db -e "create table t_plan_loan
(
	flow_id					bigint not null comment '流水ID',
	#标的信息
	asset_id				bigint not null comment '标的ID',
	business_type			bigint not null default 0 comment '业务类型ID',
	asset_type				tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
	debt_id					bigint not null comment '理财计划ID',
	financial_plain_investment_id			bigint not null default 0 comment '对应的理财计划投资ID',
	state             		int not null default 0 comment '标的状态和zyxr保持一致: 0初始化 1初审通过 2招标中 3已满标 4正在放款 5正在流标 6还款中 7还款完成 8已流标',	
	level					tinyint not null default -1 comment '优先级类型:置顶资产，新资产，计划正常退出，计划提前退出；还款复投，新计划',
	investor_uid			bigint not null default 0 comment '还款本息投资人uid',
	payoff_id				bigint not null default 0 comment '还款本息payoff_id',
	#借款信息
	borrower_uid	       	bigint not null default 0 comment '借款人uid',
	annual_rate	        	int not null default 0 comment '年化利率,万分之几',
	total_amount      		bigint not null default 0 comment '募资总额,单位为分',
	raised_amount      		bigint not null default 0 comment '已募集到金额，单位金额为分',
	#各类时间
	publish_time			datetime not null default '0000-00-00 00:00:00' comment '标的接受投标时间',
	update_time        		datetime not null default '0000-00-00 00:00:00' comment '标的更新时间',
	#匹配信息
	match_amount			bigint not null default 0 comment '匹配金额',
	left_amount				bigint not null default 0 comment '匹配剩余金额',
	match_time				datetime not null default '0000-00-00 00:00:00' comment '匹配时间',
	match_flow_id			bigint not null default -1 comment '匹配批次号',
   primary key (flow_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#理财计划投资人列表
$mysql_exec $db -e "create table t_plan_investors
(
	#通用信息
	financial_plan_id		bigint not null default 0 comment '理财计划ID',
	#投资人投资记录信息
	investor_uid       		bigint not null default 0 comment '真实投资人UID',
	financial_plain_investment_id			bigint not null default 0 comment '对应的理财计划投资ID',
	amount             		bigint not null default 0 comment '投资金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#理财计划还款本息
$mysql_exec $db -e "create table t_reinvested_money
(
	payoff_id				bigint not null default 0 comment '回款id',
	financial_plan_id		bigint not null default 0 comment '理财计划ID',
	investment_id			bigint not null default 0 comment '投资ID',
	financial_plain_investment_id			bigint not null default 0 comment '对应的理财计划投资ID',
	investor_uid			bigint not null default 0 comment '投资人uid',
	payoff_amount			bigint not null default 0 comment '回款金额',
	reinvested_amount		bigint not null default 0 comment '复投金额',
	create_time				datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	update_time 			datetime not null default '0000-00-00 00:00:00' comment '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


#理财计划还款本息流水
$mysql_exec $db -e "create table t_reinvested_money_flow
(
	flow_id	 			bigint not null default 0 comment '流水ID',
	payoff_id			bigint not null default 0 comment '回款ID',
	financial_plan_id	bigint not null default 0 comment '理财计划ID',
	plan_level			tinyint not null default -1 comment '优先级类型:置顶资产，新资产，计划正常退出，计划提前退出；还款复投，新计划',
	investment_id		bigint not null default 0 comment '投资ID',
	financial_plan_investment_id		bigint not null default 0 comment '对应的理财计划投资ID',
	investor_uid		bigint not null default 0 comment '投资人uid',
	asset_id			bigint not null default 0 comment '散标id',
	asset_level			tinyint not null default -1 comment '优先级类型:置顶资产，新资产，计划正常退出，计划提前退出；还款复投，新计划',
	change_amount		bigint not null default 0 comment '变化金额',
	remark				varchar(64) not null default '' comment '描述',
	create_time			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	update_time 		datetime not null default '0000-00-00 00:00:00' comment '更新时间',
   primary key (flow_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#理财计划匹配结果
$mysql_exec $db -e "CREATE TABLE t_plan_match_loan 
(
	flow_id	 			bigint not null default 0 comment '流水ID',
	trans_id			bigint not null default 0 comment '请求ID',
	financial_plan_id	bigint not null default 0 comment '理财计划ID',
	plan_level			tinyint not null default -1 comment '优先级类型:置顶资产，新资产，计划正常退出，计划提前退出；还款复投，新计划',
	investor_uid		bigint not null default 0 comment '投资人uid',
	financial_plain_investment_id			bigint not null default 0 comment '对应的理财计划投资ID',
	asset_id			bigint not null default 0 comment '散标id',
	asset_level			tinyint not null default -1 comment '优先级类型:置顶资产，新资产，计划正常退出，计划提前退出；还款复投，新计划',
	debt_financial_plan_id		bigint not null default 0 comment '债权转让标对应的理财计划id',
	match_amount		bigint not null default 0 comment '复投金额',
	invest_state		tinyint not null default 0 comment '投资结果',
	create_time			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	update_time 		datetime not null default '0000-00-00 00:00:00' comment '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
