#!/bin/bash
#################################################################
# 库列表：
# loan: 	核心业务库,负责标的表数据
# invest：	投资库，负责投资相关表数据
# repay: 	还款库，负责还款相关表数据
#################################################################

#password="123456"
m=0
n=0
l=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "



###################
#loan库			  #
###################
db=product
$mysql_exec -e "create database ${db}"

#标的表
$mysql_exec $db -e "create table t_assets
(
	#标的信息
	asset_id			bigint not null comment '标的ID',
	category_type		bigint not null default 0 comment '业务类别ID，1-车融宝，2-惠农宝，3-企融宝，4-医融宝，5-信用宝，6-供应链宝，7-房融宝',
	business_type		bigint not null default 0 comment '业务类型ID',
	business_name		varchar(64) not null comment '业务类型名称',
	asset_name          varchar(64) not null comment '产品标题，zyxr的数据库设计为128，实际最大数据为35',
	asset_type			tinyint not null default 0 comment '标的类型:散标,理财计划虚拟标',
	asset_pool			tinyint not null default 0 comment '理财计划池/散标池',
	asset_property		bigint not null default 0 comment '标的属性',
	publish_platform	bigint not null default 0 comment '发布平台',
	contract_no			varchar(64) not null default 0 comment '借款合同编号',
	state              	int not null default 0 comment '标的状态和zyxr保持一致: 0初始化 1初审通过 2招标中 3已满标 4正在放款 5正在流标 6还款中 7还款完成 8已流标',	
	#风险等级
	credit             	varchar(32) not null default '' comment '信用评级',
	#收益说明
	asset_desc			varchar(256) not null default '' comment '产品描述',
	#借款人信息
	borrower_uid       	bigint not null default 0 comment '借款人uid',
	borrower_name		varchar(64) not null default '' comment '借款人名字',
	borrower_borrowway	int not null default 0 comment '借款方式： 1担保借款、2抵押借款、3信用借款',
	borrower_time		int not null default 0 comment '用于索引,借款期限：0:小于一个月,1:1-3个月,2:3-6个月,3:6-12个月,4:大于12个月',
	borrower_location 	varchar(255) not null default  '' comment '借款人融资方所在地',			
	#借款信息
	total_amount       	bigint not null default 0 comment '募资总额,单位为分',
	raised_amount      	bigint not null default 0 comment '已募集到金额，单位金额为分',
	money_range			int not null default 0 comment '用于索引，募集金额范围:',
	annual_rate        	int not null default 0 comment '年化利率,万分之几',
	add_rate			int not null default 0 comment '加息',
   	phase_total        	int not null default 0 comment '借款总期数',
	phase_mode			int not null default 0 comment '期数类型',
	repay_mode		 	int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
	repay_phase			int not null default 0 comment '用户已还了多少期',
	deposit_phase		int not null default 0 comment '用户已垫付多少期',
	#投标信息
	min_tender			bigint not null default 0 comment '投标时对投资金额的限制,最低允许金额,单位为分',
	increase_tender		bigint not null default 0 comment '递增金额,例如:加入金额10,000元起，且为10,000元的整数倍递增',
	max_tender			bigint not null default 0 comment '投标时对投资金额的限制,累计最高允许金额,单位为分',	
	investor_count		int not null default 0 comment '投资人数',
	#后台文案
	operation_doc 		varchar(128) not null default '' comment '运营文案',	
	#债权转让:
	lock_day			int not null default 0 comment '债权转让的锁定期',
	#各类时间
	publish_time		datetime not null default '0000-00-00 00:00:00' comment '显示时间',
	invest_time			datetime not null default '0000-00-00 00:00:00' comment '允许投标时间',
	bid_time 			datetime not null default '0000-00-00 00:00:00' comment '截止投标时间',
	full_time			datetime not null default '0000-00-00 00:00:00' comment '满标时间',
	finish_time			datetime not null default '0000-00-00 00:00:00' comment '实际完标时间',
	update_time        	datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	create_time        	datetime not null default '0000-00-00 00:00:00' comment '创建时间',	
	match_time			datetime not null default '0000-00-00 00:00:00' comment '理财计划匹配投资时间',	
   primary key (asset_id),
   index index_business_type (business_type),
   index index_asset_type (asset_type),
   index index_asset_pool (asset_pool),
   index index_asset_property (asset_property),
   index index_publish_platform (publish_platform),
   index index_state (state),
   index index_borrower_uid (borrower_uid),
   index index_borrower_borrowway (borrower_borrowway),
   index index_money_range (money_range),
   index index_repay_mode (repay_mode),
   index index_borrower_time (borrower_time),
   index index_publish_time (publish_time),
   index index_invest_time (invest_time),
   index index_bid_time (bid_time),
   index index_full_time (full_time),
   index index_finish_time (finish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


#标的流水表
for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	#标的流水表
	$mysql_exec $db -e "create table t_assets_flow_${m}${n}
	(
		#通用信息
		flow_id            	bigint not null default 0 comment '流水ID',
		trans_id           	bigint not null default 0 comment '触发此次交易的请求ID',
		asset_id            bigint not null default 0 comment '标的ID',
		asset_type			tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_state         int not null default 0 comment '标的转变状态：1-生成标的，2-募集中，3-募集完毕，4-已放款，5-还款中，6-还款完毕',	     
		#流水操作
		operation          	int not null default 0 comment '操作:1-创建标的，2-发起缴费，3-缴费成功，4-募集完毕，5-流标，6-发起还款，7-还款成功，8-还款失败',
		bus_type           	tinyint not null default 0 comment '业务类型:投资或者还款',
		#流水涉及投资记录或者还款计划
		repyament_id       	bigint not null default 0 comment '还款计划ID',
		investment_id		bigint not null default 0 comment '投资记录ID',
		#流水涉及资金流向
		suid			 	bigint not null default 0 comment '资金源用户ID',
		duid				bigint not null default 0 comment '资金目的用户ID',
		amount             	bigint not null default 0 comment '资金变动金额',
		#其他
		remark             	varchar(64) not null default '' comment '描述',
		create_time        	datetime not null default 0 comment '创建时间',
	   primary key (flow_id),
	   index (asset_id),
	   index (operation)
	) ENGINE=InnoDB DEFAULT CHARSET = utf8 COMMENT='标的流水表,记录修改标的的流水信息';"
	
	
	#标的费率表
	$mysql_exec $db -e "create table t_assets_fee_${m}${n}
	(
		trans_id			bigint not null default 0 comment '请求ID',
		#通用信息
		financial_plan_id	bigint not null default 0 comment '理财计划ID',
		asset_id			bigint not null comment '标的ID',
		debt_id				bigint not null default 0 comment '债权ID',
		business_type		bigint not null default 0 comment '业务类型ID',
		business_name		varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
		asset_name          varchar(64) not null comment '产品标题，zyxr的数据库设计为128，实际最大数据为35',
		asset_type			tinyint not null default 0 comment '标的类型:散标,理财计划虚拟标',
		asset_pool			tinyint not null default 0 comment '理财计划池/散标池',
		asset_property		tinyint not null default 0 comment '标的属性',
		contract_no			varchar(64) not null default 0 comment '借款合同编号',
		state              	int not null default 0 comment '标的状态',	
		#相关表
		repayment_id     	bigint not null default 0 comment '还款计划ID',
		#费率
		fee_node			bigint not null default 0 comment '节点',
		fee_type			bigint not null default 0 comment '费的类型',
		fee_name			varchar(64) not null default '' comment '费率名称',
		fee_suid			bigint not null default 0 comment '资金变动源用户',
		fee_srole			int not null default 0 comment '资金变动源用户角色',
		fee_duid			bigint not null default 0 comment '资金变动目的用户',
		fee_drole			int not null default 0 comment '资金变动目的用户角色',
		fee_amount			bigint not null default 0 comment '资金变动金额',
		operation			int not null default 0 comment '操作',
		phase 				int not null default 0 comment '第几期',
		remark				varchar(64) not null default '' comment '描述',
		create_time			datetime not null default 0 comment '创建时间'
	) ENGINE=InnoDB DEFAULT CHARSET = utf8 COMMENT='标的的费率信息';"
	
done


######################
#标的模快投资记录表	 #					
######################
#根据asset id来设置标的流水
for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	# 投资记录表,根据asset资产来定位资产相关的投资记录表
	$mysql_exec $db -e "create table t_investment_${m}${n}
	(
		#通用信息
		financial_plan_id			bigint not null default 0 comment '理财计划ID',
		investment_id   			bigint not null default 0 comment '投资记录ID',
		financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
		asset_id            		bigint not null default 0 comment '标的ID',	 
		asset_type					tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_pool					tinyint not null default 0 comment '理财计划池/散标池',
		asset_state					tinyint not null default 0 comment '标的当前状态:主要是判断散标是否已经结束',
		asset_property				bigint not null default 0 comment '标的属性',
		asset_name					varchar(64) not null default '' comment '资产名称',
		annual_rate       	 		int not null default 0 comment '年化利率,单位万分之一',
		add_rate					int not null default 0 comment '加息',
		phase_total        			int not null default 0 comment '借款总期数',
		phase_mode					int not null default 0 comment '期数类型 0月 1天 2',
		repay_mode		 			int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
		contract_no					varchar(64) not null default 0 comment '借款合同编号',
		#债权
		debt_id						bigint not null default 0 comment '债权ID',
		debt_name					varchar(64) not null default '' comment '债权名称',
		#投资人投资记录信息
		investor_uid       			bigint not null default 0 comment '真实投资人UID',
		borrower_uid    	 		bigint not null default 0 comment '借款人UID',	  
		amount             			bigint not null default 0 comment '投资金额',
		valid_amount				bigint not null default 0 comment '实际持有金额',
		percentage					bigint not null default 0 comment '当前投资百分比',
		init_percentage				bigint not null default 0 comment '初始投资百分比',
		#卡券
		conpon_id					bigint not null default 0 comment '卡券号',
		conpon_type					tinyint not null default 0 comment '卡券类型',
		#该投资记录当的状态
		state              			tinyint not null default 0 comment '投资记录状态',
		debt_property				tinyint not null default 0 comment '债权转让的情况',
		from_device					tinyint not null default 0 comment '投资渠道',	
		#payoff字段
		rest_phase					int not null default 0 comment '第几期还款',
		expect_principal			bigint not null default 0 comment '预计待收本金',
		expect_interest				bigint not null default 0 comment '预计待收利息',
		expect_add_interest			bigint not null default 0 comment '预计收到的加息',
		expect_pay_platform			bigint not null default 0 comment '预计投资人交钱给平台',
		received_principal			bigint not null default 0 comment '已收到的本金',
		received_interest			bigint not null default 0 comment '已收到的利息',
		received_add_interest		bigint not null default 0 comment '已收到的加息',
		actual_pay_platform			bigint not null default 0 comment '实际投资人交钱给平台',
		received_money				bigint not null default 0 comment '已赚钱金额',
		next_payoff_day				datetime not null default '0000-00-00 00:00:00' comment '下一个回款日',
		#债权转让:
		lock_day					int not null default 0 comment '债权转让的锁定期',
		#时间信息
		update_time        			datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time        			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		finish_time					datetime not null default '0000-00-00 00:00:00' comment '结清时间',
		full_time					datetime not null default '0000-00-00 00:00:00' comment '标的满标时间',
		primary key (investment_id),
		index index_financial_plan_id (financial_plan_id),
		index index_financial_plan_investment_id (financial_plan_investment_id),
		index index_asset_id (asset_id),
		index index_asset_type (asset_type),
		index index_asset_state (asset_state),
		index index_debt_property (debt_property),
		index index_asset_pool (asset_pool),
		index index_investor_uid (investor_uid)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
done






######################
#投资模快			 #					
######################
#投资数据库
db=invest
$mysql_exec -e "create database ${db}"

for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	# 投资记录表,根据投资uid分表
	$mysql_exec $db -e "create table t_investment_${m}${n}
	(
		#通用信息
		financial_plan_id			bigint not null default 0 comment '理财计划ID',
		investment_id   			bigint not null default 0 comment '投资记录ID',
		financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
		asset_id            		bigint not null default 0 comment '标的ID',	 
		asset_type					tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_pool					tinyint not null default 0 comment '理财计划池/散标池',
		asset_state					tinyint not null default 0 comment '标的当前状态:主要是判断散标是否已经结束',
		asset_property				bigint not null default 0 comment '标的属性',
		asset_name					varchar(64) not null default '' comment '资产名称',
		annual_rate       	 		int not null default 0 comment '年化利率,单位万分之一',
		add_rate			int not null default 0 comment '加息',
		phase_total        			int not null default 0 comment '借款人总期数',
		phase_mode					int not null default 0 comment '期数类型 0月 1天 2',
		repay_mode		 			int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
		contract_no					varchar(64) not null default 0 comment '借款合同编号',
		#债权
		debt_id						bigint not null default 0 comment '债权ID',
		debt_name					varchar(64) not null default '' comment '债权名称',
		#投资人投资记录信息
		investor_uid       			bigint not null default 0 comment '真实投资人UID',
		borrower_uid    	 		bigint not null default 0 comment '借款人UID',	  
		amount             			bigint not null default 0 comment '投资金额',
		valid_amount				bigint not null default 0 comment '实际持有金额',
		percentage					bigint not null default 0 comment '当前投资百分比',
		init_percentage				bigint not null default 0 comment '初始投资百分比',
		#卡券
		conpon_id					bigint not null default 0 comment '卡券号',
		conpon_type					tinyint not null default 0 comment '卡券类型',
		#该投资记录当的状态
		state              			tinyint not null default 0 comment '投资记录状态',
		debt_property				tinyint not null default 0 comment '债权转让的情况',
		from_device					tinyint not null default 0 comment '投资渠道',	
		#payoff字段
		rest_phase					int not null default 0 comment '第几期还款',
		expect_principal			bigint not null default 0 comment '预计待收本金',
		expect_interest				bigint not null default 0 comment '预计待收利息',
		expect_add_interest			bigint not null default 0 comment '预计收到的加息',
		expect_pay_platform			bigint not null default 0 comment '预计投资人交钱给平台',
		received_principal			bigint not null default 0 comment '已收到的本金',
		received_interest			bigint not null default 0 comment '已收到的利息',
		received_add_interest		bigint not null default 0 comment '已收到的加息',
		actual_pay_platform			bigint not null default 0 comment '实际投资人交钱给平台',
		received_money				bigint not null default 0 comment '已赚钱金额',
		next_payoff_day				datetime not null default '0000-00-00 00:00:00' comment '下一个回款日',
		#债权转让:
		lock_day					int not null default 0 comment '债权转让的锁定期',
		#时间信息
		update_time        			datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time        			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		finish_time					datetime not null default '0000-00-00 00:00:00' comment '结清时间',
		full_time					datetime not null default '0000-00-00 00:00:00' comment '标的满标时间',
		primary key (investment_id),
		index index_financial_plan_id (financial_plan_id),
		index index_financial_plan_investment_id (financial_plan_investment_id),
		index index_asset_id (asset_id),
		index index_asset_type (asset_type),
		index index_asset_state (asset_state),
		index index_debt_property (debt_property),
		index index_asset_pool (asset_pool),
		index index_investor_uid (investor_uid)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	# 投资流水表
	$mysql_exec $db -e "create table t_investment_flow_${m}${n}
	(
		#通用信息
		flow_id						bigint not null default 0 comment '流水id',
		trans_id        			bigint not null default 0 comment '导致该流水的客户请求ID',
		financial_plan_id			bigint not null default 0 comment '理财计划ID',
		investment_id   			bigint not null default 0 comment '投资记录表ID',
		financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
		asset_id          			bigint not null default 0 comment '标的id',
		asset_type					tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_state					tinyint not null default 0 comment '标的当前状态:主要是判断散标是否已经结束',
		#债权
		debt_id						bigint not null default 0 comment '债权ID',
		debt_name					varchar(64) not null default 0 comment '债权名称',
		#投资人投资记录信息
		investor_uid    			bigint not null default 0 comment '真实投资人ID',
		borrower_uid  				bigint not null default 0 comment '借款人UID',	  
		#卡券
		conpon_id					bigint not null default 0 comment '卡券号',
		conpon_type					tinyint not null default 0 comment '卡券类型',
		#投资情况
		state              			tinyint not null default 0 comment '状态：',
		debt_property				tinyint not null default 0 comment '债权转让的情况',
		from_device					tinyint not null default 0 comment '投资渠道',	
		#涉及的金额数目
		amount          			bigint not null default 0 comment '投资或者债权转让金额',
		#流水操作类型
		operation          			int not null default 0 comment '操作：',
		remark            	 		varchar(64) not null default '' comment '描述',
		#时间
		create_time        		datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (flow_id),
	   index index_investment_id (investment_id),
	   index index_financial_plan_id (financial_plan_id),
	   index index_financial_plan_investment_id (financial_plan_investment_id),
	   index index_investor_uid (investor_uid),
	   index index_operation (operation)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	
	
	# 回款表
	# 还款方式导致每一期回款不同
	$mysql_exec $db -e "create table t_investment_payoff_${m}${n}
	(
		#通用信息
		payoff_id       			bigint not null auto_increment comment '投资收益单ID',
		financial_plan_id			bigint not null default 0 comment '理财计划ID',
		investment_id   			bigint not null default 0 comment '投资记录ID',
		financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
		asset_id            		bigint not null default 0 comment '标的ID',	 
		asset_name 					varchar(64) not null default '' comment '标名',
		asset_type					tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_pool					tinyint not null default 0 comment '理财计划池/散标池',
		#导致投资人有收益是哪一项投资记录
		investor_uid     			bigint not null default 0 comment '真实投资人UID',
		borrower_uid       			bigint not null default 0 comment '借款人UID',
		amount          			bigint not null default 0 comment '投资人投资金额',
		annual_rate     			int not null default 0 comment '年化利率,万分之几',	
		add_rate					int not null default 0 comment '加息',
		#导致投资人有收益是哪一项还款记录
		repayment_id     			bigint not null default 0 comment '还款计划ID',
		phase          				int not null default 0 comment '第几期收益',		
		#预计信息
		expect_principal			bigint not null default 0 comment '投资人本期预计获取的本金,单位：分',
		expect_interest 			bigint not null default 0 comment '投资人本期预计获取的利息,单位：分',
		expect_add_interest			bigint not null default 0 comment '平台给投资人的利息,单位：分',
		expect_pay_platform			bigint not null default 0 comment '投资人交钱给平台',
		expect_date 				datetime not null default '0000-00-00 00:00:00' comment '投资人本期预计获取本金收益,获取收益后给平台费率的时间',
		#实际信息
		actual_principal			bigint not null default 0 comment '投资人实际已收到的本金',
		actual_interest  			bigint not null default 0 comment '投资人实际已收到的利息',
		actual_add_interest			bigint not null default 0 comment '平台实际给投资人的加息,单位:分',
		actual_pay_platform			bigint not null default 0 comment '投资人交钱给平台',
		actual_date  				datetime not null default '0000-00-00 00:00:00' comment '投资人本期实际获取本金收益,获取收益后给平台费率的时间',
		#收益状态
		state             			tinyint not null default 0 comment '状态：1-待还，2-本期收益完毕',
		#时间信息
		update_time      			datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time      			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (payoff_id),
	   index index_investment_id (investment_id),
	   index index_investor_uid (investor_uid),
	   index index_asset_id (asset_id),
	   index index_state (state),
	   index index_repayment_id (repayment_id),
	   index index_phase (phase)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	#投资总览表
	$mysql_exec $db -e "create table t_investment_sum_${m}${n}
	(
		#通用信息
		investor_uid     			bigint not null default 0 comment '真实投资人UID',
		#定期理财
		loan_invest_sum				bigint not null default 0 comment '累计投资金额',
		loan_invest_payoff			bigint not null default 0 comment '已经赚取收益',
		loan_principal				bigint not null default 0 comment '待收本金',
		loan_interest				bigint not null default 0 comment '待收利息',
		loan_add_interest			bigint not null default 0 comment '待收加息',
		loan_invests				bigint not null default 0 comment '累计成功投资的笔数',
		#理财计划
		plan_invest_sum				bigint not null default 0 comment '累计投资金额',
		plan_invest_payoff			bigint not null default 0 comment '已经赚取收益',
		plan_principal				bigint not null default 0 comment '待收本金',
		plan_interest				bigint not null default 0 comment '待收利息',
		plan_add_interest			bigint not null default 0 comment '待收加息',
		plan_invests				bigint not null default 0 comment '当前持有债权数',		
		#债权转让
		debt_invest_sum				bigint not null default 0 comment '累计已转让初始金额',
		debt_actual_amount			bigint not null default 0 comment '实际成交金额',
		debt_invests				bigint not null default 0 comment '累计成交次数',
		#收益
		loan_yesterday_payoff			bigint not null default 0 comment '昨日定期理财收益',
		loan_today_payoff			bigint not null default 0 comment '今天定期理财收益',
		plan_yesterday_payoff			bigint not null default 0 comment '昨日理财计划收益',
		plan_today_payoff			bigint not null default 0 comment '今日理财计划收益',
		#时间信息
		update_time      			datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time      			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (investor_uid)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	
	
	
done


######################
#理财计划			 #					
######################
#根据理财计划定位投资记录表

db=financial_plan
$mysql_exec -e "create database ${db}"

for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	# 投资记录表B,根据理财计划ID分表
		# 投资记录表,根据投资uid分表
	$mysql_exec $db -e "create table t_investment_${m}${n}
	(
		#通用信息
		financial_plan_id			bigint not null default 0 comment '理财计划ID',
		investment_id   			bigint not null default 0 comment '投资记录ID',
		financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
		asset_id            		bigint not null default 0 comment '标的ID',	 
		asset_type					tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_pool					tinyint not null default 0 comment '理财计划池/散标池',
		asset_state					tinyint not null default 0 comment '标的当前状态:主要是判断散标是否已经结束',
		asset_property				bigint not null default 0 comment '标的属性',
		asset_name					varchar(64) not null default '' comment '资产名称',
		annual_rate       	 		int not null default 0 comment '年化利率,单位万分之一',
		add_rate					int not null default 0 comment '加息',
		phase_total        			int not null default 0 comment '借款总期数',
		phase_mode					int not null default 0 comment '期数类型 0月 1天 2',
		repay_mode		 			int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
		contract_no					varchar(64) not null default 0 comment '借款合同编号',
		#债权
		debt_id						bigint not null default 0 comment '债权ID',
		debt_name					varchar(64) not null default '' comment '债权名称',
		#投资人投资记录信息
		investor_uid       			bigint not null default 0 comment '真实投资人UID',
		borrower_uid    	 		bigint not null default 0 comment '借款人UID',	  
		amount             			bigint not null default 0 comment '投资金额',
		valid_amount				bigint not null default 0 comment '实际持有金额',
		percentage					bigint not null default 0 comment '当前投资百分比',
		init_percentage				bigint not null default 0 comment '初始投资百分比',
		#卡券
		conpon_id					bigint not null default 0 comment '卡券号',
		conpon_type					tinyint not null default 0 comment '卡券类型',
		#该投资记录当的状态
		state              			tinyint not null default 0 comment '投资记录状态',
		debt_property				tinyint not null default 0 comment '债权转让的情况',
		from_device					tinyint not null default 0 comment '投资渠道',	
		#payoff字段
		rest_phase					int not null default 0 comment '第几期还款',
		expect_principal			bigint not null default 0 comment '预计待收本金',
		expect_interest				bigint not null default 0 comment '预计待收利息',
		expect_add_interest			bigint not null default 0 comment '预计收到的加息',
		expect_pay_platform			bigint not null default 0 comment '预计投资人交钱给平台',
		received_principal			bigint not null default 0 comment '已收到的本金',
		received_interest			bigint not null default 0 comment '已收到的利息',
		received_add_interest		bigint not null default 0 comment '已收到的加息',
		actual_pay_platform			bigint not null default 0 comment '实际投资人交钱给平台',
		received_money				bigint not null default 0 comment '已赚钱金额',
		next_payoff_day				datetime not null default '0000-00-00 00:00:00' comment '下一个回款日',
		#债权转让:
		lock_day					int not null default 0 comment '债权转让的锁定期',
		#时间信息
		update_time        			datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time        			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		finish_time					datetime not null default '0000-00-00 00:00:00' comment '结清时间',
		full_time					datetime not null default '0000-00-00 00:00:00' comment '标的满标时间',
		primary key (investment_id),
		index index_financial_plan_id (financial_plan_id),
		index index_financial_plan_investment_id (financial_plan_investment_id),
		index index_asset_id (asset_id),
		index index_asset_type (asset_type),
		index index_asset_state (asset_state),
		index index_debt_property (debt_property),
		index index_asset_pool (asset_pool),
		index index_investor_uid (investor_uid)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
done




#####################
#债权转让表			#					
#####################
db=product
$mysql_exec -e "create database ${db}"

#债权转让表
$mysql_exec $db -e "create table t_debts
(
	#通用信息
	debt_id        			bigint not null default 0 comment '债权转让id',   
	debt_name 				varchar(64) not null default '' comment '债权转让标题',
	financial_plan_id		bigint not null default 0 comment '债权对应的投资记录是否属于某一个理财计划ID',
	business_type			bigint not null default 0 comment '业务类型ID',
	business_name			varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
	asset_id            	bigint not null default 0 comment '债权对应的标的ID',	 
	asset_type				tinyint not null default 0 comment '债权对应的标的类型,标的的类型:散标,理财计划虚拟标',
	asset_pool				tinyint not null default 0 comment '理财计划池/散标池',
	asset_property			bigint not null default 0 comment '标的属性',
	asset_name				varchar(64) not null default '' comment '债权对应的散标名称',
	borrower_uid			bigint not null default 0 comment '债权对应的标的的借款人',
	contract_no				varchar(64) not null default 0 comment '借款合同编号',
	phase_total        		int not null default 0 comment '借款人总期数',
	phase_mode				int not null default 0 comment '期数类型 0月 1天 2',
	annual_rate       	 	bigint not null default 0 comment '年化利率,单位万分之一',
	add_rate				bigint not null default 0 comment '加息',
	#债权转让的基本信息
	investment_id   		bigint not null default 0 comment '债权对应的投资记录ID',
	financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
	transferor_uid			bigint not null default 0 comment '出让人uid',
	transfer_mode			tinyint not null default 0 comment '转让方式:全部转让',
	repay_mode		 		tinyint not null default 0 comment '还款方式:一次性还本付息',	
	debt_price				bigint not null default 0 comment '债权价值',
	debt_transfer_price		bigint not null default 0 comment '转让价格',
	debt_discount_rate		bigint not null default 0 comment '折让率',	
	#债权转让信息
	amount					bigint not null default 0 comment '债权转让的金额',
	raised_amount			bigint not null default 0 comment '已经转让的金额',
	commission_amount		bigint not null default 0 comment '转让手续费',
	investors				int not null default 0 comment '投资人数',	
	state					int not null default 0 comment '债权转让表状态',
	#投标金额限制
	min_tender				bigint not null default 0 comment '投标时对投资金额的限制,最低允许金额,单位为分',
	increase_tender			bigint not null default 0 comment '递增金额,例如:加入金额10,000元起，且为10,000元的整数倍递增',
	max_tender				bigint not null default 0 comment '投标时对投资金额的限制,累计最高允许金额,单位为分',	
	#时间信息
	transfer_time			datetime not null default '0000-00-00 00:00:00' comment '转让时效',
	full_time				datetime not null default '0000-00-00 00:00:00' comment '标的满标时间',
	update_time				datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	create_time       		datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	update_debt_price_time				datetime not null default '0000-00-00 00:00:00' comment '更新债权价值时间',
   primary key (debt_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"



#债权转让流水表
$mysql_exec $db -e "create table t_debts_flow
(
	#通用信息
	flow_id							bigint not null default 0 comment '流水ID',
	financial_plan_id				bigint not null default 0 comment '债权对应的投资记录是否属于某一个理财计划ID',
	asset_id            			bigint not null default 0 comment '债权对应的标的ID',	 
	asset_type						tinyint not null default 0 comment '债权对应的标的类型,标的的类型:散标,理财计划虚拟标',
	debt_id        					bigint not null default 0 comment '债权转让id',   
	debt_name 						varchar(64) not null default '' comment '债权转让标题',
	business_type					bigint not null default 0 comment '业务类型ID',
	business_name					varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
	#债权转让信息
	#出让人的投资记录
	transferor_uid					bigint not null default 0 comment '出让人uid',
	transferor_investment_id   		bigint not null default 0 comment '债权对应的投资记录ID',
	#受让人的投资记录
	assignee_uid					bigint not null default 0 comment '受让人uid',
	assignee_investment_id			bigint not null default 0 comment '受让人的投资计划表',
	#受让人投资方式
	invest_type						tinyint not null default 0 comment '投资方式',
	state							int not null default 0 comment '债权转让表状态',
	#债权转让信息
	amount							bigint not null default 0 comment '债权转让金额',
	commission_amount				bigint not null default 0 comment '转让手续费',
	#时间信息
	operation						int not null default 0 comment '债权转让状态:发起转账请求,转账失败,转账成功',
	create_time       				datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	primary key (flow_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"



#####################
#还款			 	#					
#####################
db = product


$mysql_exec -e "create database ${db}"
for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	
	# 还款计划表
	$mysql_exec $db -e "create table t_repayment_${m}${n}
	(
		#通用信息
		repayment_id   			bigint not null default 0 comment '还款计划ID',
		asset_id        		bigint not null default 0 comment '标的id',   
		asset_type				tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_name          	varchar(64) not null comment '产品标题',
		annual_rate        		bigint not null default 0 comment '年化利率,万分之几',
		#借款人信息
		contract_no				varchar(64) not null default 0 comment '借款合同编号',
		repay_mode		 		int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
		borrower_uid  			bigint not null default 0 comment '借款人uid',
		amount    				bigint not null default 0 comment '还款金额',
		phase_total        		int not null default 0 comment '借款总期数',
		phase_mode				int not null default 0 comment '期数类型 0月 1天 2年',
		#第几期
		phase           		int not null default 0 comment '第几期还款',
		#预计信息
		expect_principal		bigint not null default 0 comment '本期预计需要还款的本金,单位为分',
		expect_interest 		bigint not null default 0 comment '本期预计需要还款的利息,单位为分',
		expect_penalty 			bigint not null default 0 comment '应收逾期罚金,单位为分',
		expect_compensation		bigint not null default 0 comment '逾期违约金',
		expect_pay_guarantee	bigint not null default 0 comment '本期预计给担保商的费率',
		expect_pay_incoming		bigint not null default 0 comment '本期预计给进件方的费率',
		expect_pay_platform		bigint not null default 0 comment '本期预计给平台的费率',
		expect_pay_fee			bigint not null default 0 comment '本期借款人需要交纳的各种费用',
		expect_date 			datetime not null default '0000-00-00 00:00:00' comment '本期应还款日期',
		#公司垫付
		guarantee_id			bigint not null default 0 comment '担保角色的UID',
		guarantee_role			tinyint not null default 0 comment '担保角色:担保公司,进件方,平台',
		guarantee_name			varchar(64) not null default '' comment '担保角色的用户名', 
		guarantee_principal		bigint not null default 0 comment '本期担保公司垫付本金',
		guarantee_interest 		bigint not null default 0 comment '本期担保公司垫付利息',
		guarantee_pay_guarantee	bigint not null default 0 comment '本期垫付给担保商的费率',
		guarantee_pay_incoming	bigint not null default 0 comment '本期垫付给进件方的费率',
		guarantee_pay_platform	bigint not null default 0 comment '本期垫付给平台的费率',
		guarantee_pay_fee		bigint not null default 0 comment '本期垫付交纳的各种费用',
		guarantee_date			datetime not null default '0000-00-00 00:00:00' comment '本期担保公司垫付日期',
		guarantee_gains 		bigint not null default 0 comment '本期担保公司因垫付获取的收益',
		guarantee_gains_date	datetime not null default '0000-00-00 00:00:00' comment '本期担保公司获取收益的日期',
		#借款人实际还款信息
		actual_principal 		bigint not null default 0 comment '本期实际已还本金',
		actual_interest  		bigint not null default 0 comment '本期实际已还利息',
		actual_penalty   		bigint not null default 0 comment '本期实际已缴罚金',	
		actual_compensation		bigint not null default 0 comment '本期实际已缴违约金',
		actual_pay_guarantee	bigint not null default 0 comment '本期实际担保商的费率',
		actual_pay_incoming		bigint not null default 0 comment '本期实际进件方的费率',
		actual_pay_platform		bigint not null default 0 comment '本期实际给平台的费率',
		actual_pay_fee			bigint not null default 0 comment '本期实际交纳的各种费用',
		actual_date				datetime not null default '0000-00-00 00:00:00' comment '实际清偿日期',
		#还款计划的状态信息
		state         		int not null default 0 comment '状态：',
		properties        	int not null default 0 comment '属性位：1-逾期，2-提前还款',
		#时间信息
		update_time       	datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	    primary key (repayment_id),
		index index_borrower_uid (borrower_uid),
		index index_asset_id (asset_id),
		index index_asset_type (asset_type),
		index index_state (state),
		index index_phase (phase),
		index index_expect_date (expect_date)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	$mysql_exec $db -e "create table t_repayment_flow_${m}${n}
	(
	    flow_id				bigint not null default 0 comment '流水ID',
        trans_id			bigint not null default 0 comment '事务ID,前端请求ID',
        repayment_id		bigint not null default 0 comment '还款计划ID',
        asset_id			bigint not null default 0 comment '标的id',
        asset_type			tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
        borrower_uid		bigint not null default 0 comment '借款人uid',
        #本息
		principal			bigint not null default 0 comment '还款本金',
        interest			bigint not null default 0 comment '还款利息',
		#各种费率
        penalty				bigint not null default 0 comment '还款罚金',
        compensation		bigint not null default 0 comment '违约金',
		pay_guarantee		bigint not null default 0 comment '本期垫付担保商的费率',
		pay_incoming		bigint not null default 0 comment '本期垫付进件方的费率',
		pay_platform		bigint not null default 0 comment '本期垫付给平台的费率',
		#其他
        phase				int not null default 0 comment '第几期还款',
        state				int not null default 0 comment '状态',
		operation_uid		bigint not null default 0 comment '请求人uid',
		operation_name		varchar(64) not null default '' comment '请求人名字',
		operation_role		bigint not null default 0 comment '请求人角色:用户,担保公司,进件方,平台',
        operation			int not null default 0 comment '操作',
        create_time			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		primary key (flow_id),
		index index_repayment_id(repayment_id),
		index index_asset_id (asset_id),
		index index_borrower_uid (borrower_uid),
		index index_operation (operation)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
done



