#!/bin/bash
#################################################################
#password=123456
m=0
n=0
l=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "
db=reward

$mysql_exec -e "create database ${db}"

#优惠券类型
$mysql_exec $db -e "CREATE TABLE t_coupon_type (
  id 			 		bigint(20) NOT NULL AUTO_INCREMENT  COMMENT '优惠券类型ID',
  name     		 		varchar(64) NOT NULL                COMMENT '名称',
  type 	 		 		tinyint(4) NOT NULL DEFAULT '0'     COMMENT '优惠券类型 1-现金券 2-体验金',
  amount 	     		bigint NOT NULL  DEFAULT 0          COMMENT '优惠券面值(保存分，如果是百分比 保存万分之一)',
  unit 	      	 		varchar(16) NOT NULL DEFAULT ''     COMMENT '单位，如 元，%',
  product_limit     	varchar(16) NOT NULL            	COMMENT '适用产品：  |100| 100体验标 0-散标，1-理财计划, 2债权转让  ',
  product_tag_limit		varchar(16) NOT NULL DEFAULT '|0|'  COMMENT '适用标签： |0| 普通  |1|新手标',
  amount_limit 	 		bigint  NOT NULL DEFAULT 0  		COMMENT '投资金额限制 0 不限制  或者 >=X元 ',
  day_limit 	 		int  NOT NULL DEFAULT 0  			COMMENT '产品天数限制:0 不限制  或者 >=X天 ', 
  rule 		     		varchar(250) DEFAULT ''  			COMMENT '使用的规则，根据具体券；如投x-y返多少a ',
  description   		varchar(1024) DEFAULT ''  			COMMENT '使用说明', 
  status 		 		tinyint(2) NOT NULL DEFAULT 0  	 COMMENT '状态：0-启用 1 禁用',
  creator 		 		varchar(32) NOT NULL DEFAULT 'admin'  COMMENT '创建人',
  create_time 	 		datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  update_user 	 		varchar(32) DEFAULT ''                COMMENT '修改人',
  update_time 			datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY (name),
  KEY (type),
  KEY (product_limit),
  KEY (product_tag_limit),
  KEY (amount_limit),
  KEY (day_limit),
  KEY (status),
  KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '优惠券类型';"


#优惠券派发批次表
$mysql_exec $db -e "CREATE TABLE t_coupon_dispatch (
  id 			   	bigint(20) NOT NULL AUTO_INCREMENT 	COMMENT '派发Id',
  batch_no   		varchar(64) DEFAULT ''    		    COMMENT '派发批号',
  coupon_type_id	bigint(20) NOT NULL 				COMMENT '优惠券类型id',
  source 			varchar(128) DEFAULT ''    		    COMMENT '来源：后台派发，活动赠送（对应活动名称）',
  description 		varchar(250) DEFAULT ''    		    COMMENT '活动描述',
  start_time 	   	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '有效期开始时间',
  end_time 		   	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '有效期结束时间',
  count  			int  	NOT NULL DEFAULT 1			COMMENT '派发总张数',
  status 		 	tinyint NOT NULL                    COMMENT '状态：0-正常 1 回收 ',
  creator 		 	varchar(32) NOT NULL DEFAULT 'admin' COMMENT '创建人',
  create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  update_user 	 	varchar(32) DEFAULT '' COMMENT '修改人',
  update_time 		datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY (batch_no),
  KEY (coupon_type_id),
  KEY (source),
  KEY (status),
  KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '优惠券派发记录表';"


#优惠券派发详细表
$mysql_exec $db -e "CREATE TABLE t_coupon_dispatch_detail (
  id 			   	bigint(20) NOT NULL AUTO_INCREMENT 	COMMENT 'id',
  coupon_type_id	bigint(20) NOT NULL 				COMMENT '优惠券类型id',
  dispatch_id	    bigint(20) NOT NULL 				COMMENT '派发id',
  userid  			bigint(20) NOT NULL 				COMMENT '用户id',
  status 		 	tinyint NOT NULL DEFAULT 0			COMMENT '状态：0成功 1回收', 
  create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY (coupon_type_id),
  KEY (dispatch_id),
  KEY (userid),
  KEY (status),
  KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '优惠券派发详细';"


for i in {0..199}
do
l=$(($i/100))
m=$((($i-$l*100)/10))
n=$(($i%10))

# 用户优惠券表
$mysql_exec $db -e "create table t_coupon_${l}${m}${n}
(
  id 			   		bigint(20) NOT NULL 	            COMMENT '优惠券id',
  userid				bigint(20) NOT NULL 	            COMMENT '用户id',
  dispatch_id	    	bigint(20) DEFAULT 0 				COMMENT '派发id',
  coupon_type_id		bigint(20) NOT NULL 				COMMENT '优惠券类型id',
  name     		    	varchar(64) NOT NULL                COMMENT '名称',
  type 	 		    	tinyint(4) NOT NULL         		COMMENT '优惠券类型 1-现金券 2-体验金',
  amount 	        	bigint NOT NULL  DEFAULT 0          COMMENT '优惠券面值(保存分，如果是百分比 保存万分之一)',
  unit 	      	    	varchar(16) NOT NULL DEFAULT ''     COMMENT '单位，如 元，%',
  product_limit     	varchar(16) NOT NULL            	COMMENT '适用产品：  |100| 100体验标 0-散标，1-理财计划, 2债权转让 ',
  product_tag_limit		varchar(16) NOT NULL DEFAULT '|0|'  COMMENT '适用标签： |0| 普通  |1|新手标',
  amount_limit 	    	bigint  NOT NULL DEFAULT 0  		COMMENT '投资金额限制 0 不限制  或者 >=X元 ',
  day_limit 	    	int  NOT NULL DEFAULT 0  			COMMENT '产品天数限制:0 不限制  或者 >=X天 ', 
  rule 		        	varchar(250) DEFAULT ''  			COMMENT '使用的规则，根据具体券；如投x-y返多少a ',
  description       	varchar(1024) DEFAULT ''  			COMMENT '使用说明', 
  start_time 	   		datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '有效期开始时间',
  end_time 		   		datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '有效期结束时间',
  status 				tinyint  NOT NULL DEFAULT 0 					COMMENT '状态：0-有效, 1-已使用, 2-禁用, 3-锁定 ',
  use_time 				datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '优惠券使用时间',
  create_time 	    	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  update_time 	    	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY (userid),
  KEY (coupon_type_id),
  KEY (start_time),
  KEY (end_time),
  KEY (product_limit),
  KEY (product_tag_limit),
  KEY (amount_limit),
  KEY (day_limit),
  KEY (status),
  KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户优惠券表';"

#使用优惠券 按userid分库
$mysql_exec $db -e "CREATE TABLE t_coupon_use_${l}${m}${n} (
  id 			   	bigint(20) NOT NULL                 COMMENT 'id',
  txnid			    bigint(20) NOT NULL                 COMMENT '事务Id',
  userid			bigint(20) NOT NULL 	            COMMENT '用户id',
  coupon_id			bigint(20) NOT NULL 				COMMENT '优惠券id',
  product_id 		bigint(20) NOT NULL  				COMMENT '项目id',
  product_type 		tinyint    NOT NULL  				COMMENT '项目类型 100体验标 0-散标，1-理财计划, 2债权转让  ',
  product_tag 		tinyint    NOT NULL  				COMMENT '项目标签属性',
  invest_id 		bigint(20) NOT NULL default 0    	COMMENT '投资id',
  invest_amount 	bigint     NOT NULL 			    COMMENT '投资金额',
  status			tinyint  default  1                 COMMENT '0-锁定 1:使用 2解锁',
  create_time 	    datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  update_time 	    datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY (txnid),
  KEY (userid),
  KEY (coupon_id),
  KEY (product_id),
  KEY (product_type),
  KEY (product_tag),
  KEY (invest_id),
  KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '使用优惠券';"


#奖励派发计划 按userid分库
$mysql_exec $db -e "CREATE TABLE t_coupon_interest_task_${l}${m}${n} (
  id 			   	bigint(20) NOT NULL                 COMMENT 'id',
  coupon_use_id		bigint(20) default 0	            COMMENT '用户使用id',
  product_id 		bigint(20) NOT NULL  				COMMENT '项目id',
  product_type 		tinyint    NOT NULL  				COMMENT '项目类型 100体验标 0-散标，1-理财计划, 2债权转让  ',
  product_tag 		tinyint    NOT NULL  				COMMENT '项目标签属性',
  invest_id 		bigint(20) NOT NULL 				COMMENT '投资id',
  userid 		    bigint(20) NOT NULL 	            COMMENT '用户id',
  total_period		int  NOT NULL default 1 	        COMMENT '总期数',
  current_period	int  NOT NULL default 1 	        COMMENT '第几期',
  interest 	        bigint   NOT NULL DEFAULT 0         COMMENT '奖励金额',
  expected_time 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预计时间',
  actual_time 	    datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '实际奖励时间',
  type  			tinyint  NOT NULL DEFAULT  0 					COMMENT '类型：0-到时间自动触发, 1-MQ监听触发 ',
  status 			tinyint  NOT NULL DEFAULT 0 					COMMENT '状态：0-待奖励, 1-已奖励  2不奖励 3失败',
  create_time 	    datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  update_time 	    datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  fail_times 	    int NOT NULL DEFAULT 0                          COMMENT '失败次数,第一次重试3次，后系统再尝试3次',
  transid 	        varchar(64) NOT NULL DEFAULT ''                 COMMENT '交易流水ID',
  ext 	            varchar(64) NOT NULL DEFAULT ''                 COMMENT '保留',
  PRIMARY KEY (id),
  KEY (coupon_use_id),
  KEY (product_id),
  KEY (product_type),
  KEY (product_tag),
  KEY (invest_id),
  KEY (userid),
  KEY (total_period),
  KEY (current_period),
  KEY (expected_time),
  KEY (status),
  KEY (create_time),
  KEY (transid),
  KEY (status,type,expected_time),
  KEY (status,fail_times)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '奖励派发计划';"


#体验标用户投资记录 按userid分库
$mysql_exec $db -e "CREATE TABLE t_taste_invest_record_user_${l}${m}${n} 
(
    id       			bigint not null    				 comment 'ID',
	taste_id       		bigint not null  				 comment '体验标ID',
	userid				bigint not null  				 comment '用户ID',
	plantform 	 	    tinyint not null default 0 		 COMMENT '投资平台，0 pc,1 android 2 ios ',
    channel 	 	    varchar(32) not null default ''  COMMENT '投资渠道',
	coupon_id           bigint not null 				 comment '体验券id',
	amount				bigint not null default 0 comment '投资金额（券面值）',
    create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   primary key (id),
   KEY (taste_id),
   KEY (userid),
   KEY (plantform),
   KEY (channel),
   KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '体验标用户投资记录';"

# 用户奖励总计表
$mysql_exec $db -e "create table t_reward_total_${l}${m}${n}
(
    userid						     bigint not null  				  comment '用户ID',
	total_interest				     bigint not null default 0 comment '奖励系统，累计总收益A(A=B+C)',
	total_received_interest  	     bigint not null default 0 comment '奖励系统，已收累计总收益B',
	total_receivable_interest  		 bigint not null default 0 comment '奖励系统，待收累计总收益C',
	taste_total_invest_amount  	     bigint not null default 0 comment '体验标:累计投资总金额',
	taste_total_received_interest	 bigint not null default 0 comment '体验标:累计已收总收益',
	taste_total_receivable_interest	 bigint not null default 0 comment '体验标:当前待收总收益',
    create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   primary key (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户奖励总计';"

done




#体验标
$mysql_exec $db -e "CREATE TABLE t_taste (
    id       			bigint not null  AUTO_INCREMENT  comment '标的ID',
	name				varchar(64) not null comment '标名称',
	code            	varchar(64) not null comment '标的编码',
    activity_title      varchar(1024) not null default '' comment '活动标题',
	amount				bigint not null default 0 comment '标的总金额',
	rate   				bigint not null default 0 comment '预期年化收益率',
    period_type	        int not null default 0 comment '0 天 1月',
	period	        	int not null default 0 comment '计息周期', 
	interest_time       int not null default 0 comment '计息时间: 0-T+0, 1-T+1', 
	repay_type          int not null default 0 comment '还款类型: 0-一次性付息',
	amount_invested     bigint not null default 0 comment '已投资金额',
	amount_available    bigint not null default 0 comment '剩余可投资金额',
	amount_limit        bigint not null default 0 comment '累计投资上限 0 不限制', 
	publish_time        datetime not null default '0000-00-00 00:00:00' comment '发布时间',
	start_time          datetime not null default '0000-00-00 00:00:00' comment '招标开始时间',
	end_time            datetime not null default '0000-00-00 00:00:00' comment '招标截止时间',
	description 		text not null  comment '产品描述',
	status              tinyint not null default 0 comment	'体验标状态：0   （待发布，预发布，募集中 都是0，根据时间显示不同） 1 还款中  2  已结清  3 已撤销', 
	finish_time         datetime not null default '0000-00-00 00:00:00' comment '满标时间',
	creator 		 	varchar(32) NOT NULL DEFAULT 'admin' COMMENT '创建人',
	create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_user 	 	varchar(32) DEFAULT ''     COMMENT '修改人',
    update_time 		datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
    primary key (id),
	KEY (name),
	UNIQUE KEY (code),
	KEY (amount),
	KEY (rate),
    KEY (period_type),
	KEY (period),
	KEY (interest_time),
	KEY (repay_type),
	KEY (publish_time),
	KEY (start_time),
	KEY (end_time),
	KEY (status),
    KEY (status,amount_available,publish_time,end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '体验标';"


# 体验标投资记录
$mysql_exec $db -e "create table t_taste_invest_record
(
    id       			bigint not null  				 comment 'ID',
	taste_id       		bigint not null  				 comment '体验标ID',
	userid				bigint not null  				 comment '用户ID',
	plantform 	 	    tinyint not null default 0 		 COMMENT '投资平台，0 pc,1 android 2 ios ',
    channel 	 	    varchar(32) not null default ''  COMMENT '投资渠道',
	coupon_id           bigint not null 				 comment '体验券id',
	amount				bigint not null default 0 comment '投资金额（券面值）',
    create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   primary key (id),
   KEY (taste_id),
   KEY (userid),
   KEY (plantform),
   KEY (channel),
   KEY (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '体验标投资记录';"




for i in {0..99}
do
m=$(($i/10))
n=$(($i%10))

# 用户体验标投资合计表
$mysql_exec $db -e "create table t_taste_user_invest_total_${m}${n}
(
    taste_id			bigint not null  				  comment '标ID',
    userid				bigint not null  				  comment '用户ID',
	invest_count 		int 							  comment '投资次数',
	total_amount		bigint not null default 0 		  comment '累计投资金额',
    create_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time 	 	datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   primary key (taste_id,userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户体验标投资合计表';"
done



$mysql_exec $db -e "insert into reward.t_coupon_type (id,name,type,amount,unit,product_limit,product_tag_limit,   amount_limit,day_limit,rule,description,status,  creator,create_time) 
values 
(1,'区间现金券200',1,20000,'元','|0|1|',  '|0|1|',100000,30,'100000,2000|500000,5000|1000000,10000|2000000,20000','投资>=1000元，返现20元现金；投资>=5000元，返现50元现金；投资>=10000元，返现100元现金；投资>=2000元，返现200元现金；该券为一次性使用券，使用后将会被标记为已使用，不可重复使用。', 0,'admin',now()),
(2,'体验金8888',2,888800,'元','|100|',  '|0|',0,0,'','', 0,'admin',now()), 
(3,'现金券8',1,800,'元','|0|1|',  '|0|1|',50000,30,'50000,800','投资>=500元，期限≥1个月，返现8元；', 0,'admin',now()), 
(4,'现金券28',1,2800,'元','|0|1|',  '|0|1|',500000,30,'500000,2800','投资>=5000元，期限≥1个月，返现28元。', 0,'admin',now()), 
(5,'现金券88',1,8800,'元','|0|1|',  '|0|1|',1000000,90,'1000000,8800','投资>=10000元，期限≥3个月，返现88元。', 0,'admin',now()), 
(6,'现金券218',1,21800,'元','|0|1|',  '|0|1|',2000000,90,'2000000,21800','投资>=20000元，期限≥3个月，返现218元。', 0,'admin',now()), 
(7,'现金券358',1,35800,'元','|0|1|',  '|0|1|',3000000,180,'3000000,35800','投资>=30000元，期限≥6个月，返现358元。', 0,'admin',now()), 
(8,'现金券588',1,58800,'元','|0|1|',  '|0|1|',5000000,180,'5000000,58800','投资>=50000元，期限≥6个月，返现588元。', 0,'admin',now());"