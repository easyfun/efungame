#!/bin/bash
#################################################################
#################################################################
password=123456

m=0
n=0

#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "


#zyxr 基础库
db=asset

$mysql_exec -e "create database $db;"

$mysql_exec $db -e "create table t_cooperation
(
	company_id  		bigint not null comment '合作机构分配的ID',
	company_name		varchar(128) not null comment '合作机构的机构名称',
	company_type		int not null comment '合作机构的类型：0-无，1-平台，2-进件方，3-担保公司,4-融资租赁公司,5-风险备付金',
	register_name		varchar(64) not null default '' comment '合作机构注册时的用户名',
	register_mobile		bigint not null  comment '合作机构注册时的手机号码',
	register_idcard		varchar(64) not null  default '' comment '合作机构注册时的身份证ID',
	bank_account_name	varchar(128) not null default '' comment '合作机构注册时使用的银行账户名称',
	bank_account_id		varchar(64) not null default '' comment '合作机构注册时使用的银行账号',
	
	artif_name			varchar(128) not null default '' comment '法人姓名',
	bank_province_id	varchar(128) not null default '' comment '银行卡所在省',
	bank_city_id		varchar(128) not null default '' comment '银行卡所在市',
	bank_code			varchar(128) not null  default '' comment '银行编号',
	email 				varchar(128) not null default '' comment '邮箱',
	
	company_desc		varchar(256) default '' comment '合作机构的具体说明',
	update_time			datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
	create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	
	
	primary key(company_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;"	




#业务类型中的信息披露属性表
#例如：
#section：借款人基础信息
#keyname:姓名
#keyname：性别
#keyname：年龄
#用这张表来保持信息披露的属性
#这张表到时运营人员可能会新增属性等,故此用sectoin-key-value保持
#注意：不支持有同名key,目前只支持单选，暂不支持多选
$mysql_exec $db -e "create table t_attr
(	
	scope_id			int not null default 0 comment '范畴ID,属于哪一个范畴',
	scope_name		varchar(64) not null default '' comment '范畴名称',
	section_id		int not null default 0 comment '属于哪一个段',
	section_name		varchar(64) not null default '' comment '段名',
	section_type      tinyint not null default 0 comment '状态：1-input框，2-checkbox框，3-其他',
	key_id			int not null default 0 comment 'key的id',
	key_name			varchar(64) not null default '' comment 'key的名字',
	primary key (scope_id,section_id,key_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务类型信息披露部分中的属性表';"


#借款项目基本信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',0,'借款项目基本信息',1,0,'项目描述');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',0,'借款项目基本信息',1,1,'还款保障措施');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',0,'借款项目基本信息',1,2,'资产大类还款保障性措施');"



#借款人基础信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,0,'姓名');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,1,'性别');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,2,'年龄');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,3,'学历');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,4,'婚姻');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,5,'手机号');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,6,'现住址');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,7,'户籍所在地');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values(0,'',1000,'借款人基础信息',1,8,'家庭情况');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,9,'所在地区');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,10,'身份证号');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,11,'配偶姓名');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,12,'配偶身份证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,13,'紧急联系人手机号');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',1000,'借款人基础信息',1,14,'紧急联系人姓名');"


#借款人资产信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',2000,'借款人资产信息',1,0,'年收入');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',2000,'借款人资产信息',1,1,'房产');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',2000,'借款人资产信息',1,2,'房贷');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',2000,'借款人资产信息',1,3,'车产');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',2000,'借款人资产信息',1,4,'车贷');"

#借款人工作信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',3000,'借款人工作信息',1,0,'公司行业');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',3000,'借款人工作信息',1,1,'公司规模');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',3000,'借款人工作信息',1,2,'工作城市');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',3000,'借款人工作信息',1,3,'工作年限');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',3000,'借款人工作信息',1,4,'职位');"

#企业相关信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,0,'公司全称');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,1,'注册资本');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,2,'注册地址');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,3,'成立时间');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,4,'实缴资本');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,5,'法定代表人');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,6,'办公地点');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',4000,'企业相关信息',1,7,'经营区域');"


#借款人车辆信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,0,'车辆所有人');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,1,'车辆品牌');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,2,'车辆型号');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,3,'车牌号码');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,8,'使用时间');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,4,'行驶公里');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,7,'购买价格');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,5,'评估价格');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,6,'抵押价格');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',5000,'借款人车辆信息',1,9,'里程数');"

#消费信息
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',6000,'消费信息',1,0,'消费内容');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',6000,'消费信息',1,1,'消费地点');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',6000,'消费信息',1,2,'商家名称');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',6000,'消费信息',1,3,'是否参加保险');"


#审核项目
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,0,'借款人身份证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,1,'借款人户口本');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,2,'借款人结婚证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,3,'借款人配偶身份证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,4,'身份证明');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,5,'收入证明');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,6,'车辆保险');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,7,'车辆登记证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,8,'车辆行驶证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,9,'车辆交强险保单');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,10,'车辆评估证明');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,11,'车辆商业保单');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,12,'车辆抵押申请表');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,13,'车辆照片');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,14,'工作认证（私营企业主）');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,15,'工作认证');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,16,'公司营业执照');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,17,'企业征信报告');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,18,'税务登记证明');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,19,'信用报告');"
$mysql_exec $db -e "insert into  t_attr(scope_id,scope_name,section_id,section_name,section_type,key_id,key_name) values (0,'',7000,'审核项目',2,20,'实地认证');"


#业务类型表第一部分
#业务类型表中的基本信息
#保持业务类型的基本信息,目前就业务名称
$mysql_exec $db -e "create table t_business_base
(
	asset_type			bigint not null default 0 comment '业务类型ID',
	asset_name			varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
	create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	update_time			datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
	primary key (asset_type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='务类型表中的基本信息';"

#业务类型表第二部分
#业务类型中的信息披露部分
#例如：
#section：借款人基础信息
#keyname:姓名
#keyname：性别
#keyname：年龄
#用这张表来保持信息披露的属性
#这张表到时运营人员可能会新增属性等,故此用sectoin-key-value保持
#如果修改了业务，然后对原来已经发表的标的不受影响,原有的表有自己的copy
#对初审或者终审的标的是否有影响，待确认
$mysql_exec $db -e "create table t_business_publish
(
	#通用部分
	asset_type	bigint not null default 0 comment '业务类型ID',
	asset_name	varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
	#信息披露表
	scope_id		int not null default 0 comment '范畴ID,属于哪一个范畴',
	scope_name	varchar(64) not null default '' comment '范畴名称',
	section_id	int not null default 0 comment 't_pushish_attr表中对应的section id,查看t_pushish_attr表,具体section id值',
	section_name		varchar(64) not null default '' comment 't_pushish_attr表中对应的section name,例如:借款项目基本信息|借款人基础信息|借款人资产信息|借款人工作信息|借款人车辆信息|审核项目', 
	section_type      tinyint not null default 0 comment '状态：1-input框，2-checkbox框，3-其他',
	key_id		int not null default 0 comment 't_pushish_attr表中,具体某一个key',
	key_name			varchar(64) not null default '' comment 't_pushish_attr表中对应的key, key 是mysql关键字',
	isset			tinyint not null default 0 comment '业务类型是否设置了该属性，0-没有设置,1-设置',
	isnotnull			tinyint not null default 0 comment '0-非必填,1-必填',
	isdisplay       tinyint not null default 0 comment '0-不向投资人展示,1-向投资人展示', 
	update_time   datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	create_time   datetime not null default '0000-00-00 00:00:00' comment '创建时间',		
	primary key (asset_type,scope_id,section_id,key_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='务类型表中的基本信息';"




$mysql_exec $db -e "create table t_business_fee
(	
	#通用部分
	asset_type			bigint not null default 0 comment '业务类型ID',
	asset_name			varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
	#费用配置部分
	fee_node				int not null default 0 comment	'收费节点:0-无,1-业务现场,2-放款前,3-发标前,4-还款时,5-借款结束时,6-违约发生时',
	fee_type 				int not null default 0 comment	'费用名称:0-无,1-平台服务费,2-代收咨询服务费,3-冻结首期投资人利息,4-进件服务费,5-保证金,6-GPS押金,7-GPS使用费,8-评估、查档、开户,9-抵押过户代办费,10-公证费,11-加急费,12-停车费,13-保险扣款,14-违章扣款,15-年检扣款',
	fee_comfire_type		int not null default 0 comment	'费用确认方式:0-无,1-固定费用，2-手动输入,3-按本金比例',
	fee_receiver			int not null default 0 comment 	'收费方：0-无，1-平台，2-进件方，3-担保公司，4-冻结放款时返还，5-冻结结清时返还',
	fee_standard			bigint not null default 0 comment '费用标准',
	fee_ispercent			tinyint not null default 0 comment '0-百分比,1-非百分比',
	
	#资金划扣方
	deduction_receiver		int not null default 0 comment 	'划扣方：0-无，1-平台，2-进件方，3-担保公司',
	#划扣节点				
	deduction_node			int not null default 0 comment	'划扣节点:0-无,1-放款时,2-逾期代偿,3-正常结清',
	#解冻节点
	unfrozen_node				int not null default 0 comment	'解冻节点:0-无,1-放款时,2-逾期代偿,3-正常结清',
	#unfrozenNode	unfreeze_node
		
	update_time			datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
	create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	primary key (asset_type,fee_node,fee_type,fee_receiver)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;"



#标的表
$mysql_exec $db -e "create table t_asset
(
	#标的信息
	asset_id       		bigint not null comment '标的ID',
	business_type		bigint not null default 0 comment '业务类型ID',
	business_name		varchar(64) not null comment '业务类型名称，例如：车贷|随心贷|惠农贷|企业贷',
	asset_name          varchar(64) not null comment '产品标题，zyxr的数据库设计为128，实际最大数据为35',
	
	asset_type			tinyint not null default 0 comment 
	'标的的类型:0 -未分配 1-散标 2-新手标,3-散标作为理财计划，4-债券转让,5- 散标作为置顶理财计划，6-理财计划不自动匹配债权， 7 -理财计划',
	virtual				tinyint not null default 0 comment '虚拟标的,如果是虚拟标的,用于用户体验,0-非虚拟标的，1-虚拟标的',	
	state   int not null default 0 comment 
	'标的状态和zyxr保持一致: 0初始化 1初审失败 2初审通过 3已付费  4终审失败 5终审通过  6已分配  7已发布 8招标中 9已满标 10正在放款 11正在流标 12还款中 13还款完成 14已流标',	
	#风险等级
	credit             	varchar(32) not null default 0 comment '信用评级',
	#借款描述
	remark				varchar(256) not null default  '' comment '借款描述',
	repay_guarantee   	varchar(256) not null default  '' comment '还款保障措施',
	#借款人信息
	borrower_uid       	bigint not null default 0 comment '借款人uid',
	borrower_name		varchar(64) not null default '' comment '借款人名字',
	borrower_borrowway	int not null default 0 comment '借款方式： 1担保借款、2抵押借款、3信用借款',
	borrower_time		int not null default 0 comment '借款期限',
	borrower_location 	varchar(255) not null default  '' comment '来源门店',	

	contract_no			varchar(64) not null default  '' comment '借款合同编号',
	
	#初审
	first_remark		varchar(256) not null default '' comment '初审描述',
	#first_result		tinyint not null default 0 comment '初审:0 - 不通过 1- 通过',
	
	#二审
	second_remark		varchar(256) not null default '' comment '二审描述',
	#second_result		tinyint not null default 0 comment '二审:0 - 不通过 1- 通过',
	
	#20160908添加字段
	is4_newer			tinyint not null default 0 comment '0 - 不是新手标， 1 - 新手标',
	
	#20160826添加字段
	incoming_party		bigint not null default 0 comment '进件方：1中业普惠 2中业正川 3中业众信',	
	guarantor			bigint not null default 0 comment '担保方：1遵明担保 2华敏担保 3融耀担保',
	#20160826添加字段
	mobile				bigint not null default 0 comment '手机号',
	#借款信息
	total_amount       	bigint not null default 0 comment '募资总额（分）',
	raised_amount      	bigint not null default 0 comment '已募集到金额（分)',
	annual_rate        	int not null default 0 comment '年化利率(万分之）',
   	phase_type        	int not null default 0 comment '借款人还款的总期数',
	phase_mode			int not null default 0 comment '期数类型 1天 2月  3年',
	repay_mode		 	int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
	#20160823添加字段
	annual_addrate        	int not null default 0 comment '加息年化利率(万分之）',
	#20160824添加字段
	asset_subname 		varchar(64) not null  default ''  comment '发标的标题',
	from_device 		tinyint not null default 0 comment '0表示不发布，1表示发布，最低位 - pc, 中间位 - app, 高位 - wap， 110',
	use_coupon 			tinyint not null default 0 comment '0 - 不能使用优惠券， 1 - 可以使用',
	#投标信息
	min_tender			bigint not null default 0 comment '投标时对投资金额的限制,最低允许金额（分）',
	increase_tender		bigint not null default 0 comment '递增金额,例如:加入金额10,000元起，且为10,000元的整数倍递增',
	max_tender			bigint not null default 0 comment '投标时对投资金额的限制,累计最高允许金额（分）',		
	investor_count		int not null default 0 comment '投资人数',
	#20160901 添加
	#初审时间
	first_time			datetime not null default '0000-00-00 00:00:00' comment '初审通过时间',
	#二审时间
	second_time			datetime not null default '0000-00-00 00:00:00' comment '二审通过时间',
	#各类时间
	display_time		datetime not null default '0000-00-00 00:00:00' comment '显示时间,呈现给前端时间',
	publish_time		datetime not null default '0000-00-00 00:00:00' comment '标的接受投标时间',
	bid_time 			datetime not null default '0000-00-00 00:00:00' comment '标的截止投标时间',
	lock_day			int not null default 0  comment '锁定期',
	bid_day 			int not null default 0  comment '募集期:天',
	update_time        	datetime not null default '0000-00-00 00:00:00' comment '标的更新时间',
	create_time        	datetime not null default '0000-00-00 00:00:00' comment '标的创建时间',	
	#20160918 添加字段
	full_time			datetime not null default '0000-00-00 00:00:00' comment '满标时间',	
	firstinvest_time	datetime not null default '0000-00-00 00:00:00' comment '首投时间',	
   primary key (asset_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


#标的流水表
for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	#标的流水表
	$mysql_exec $db -e "create table t_asset_flow_${m}${n}
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
		create_time        datetime not null default '0000-00-00 00:00:00' comment '标的创建时间',	
	   primary key (flow_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的流水表,记录修改标的的流水信息';"
	
done

#标的表2
#标的属性表,包括：
#1标的的运营属性
#2剥离标的表中非频繁访问的数据属性
#3理财计划标里面包括其他散标的标的信息
for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	$mysql_exec $db -e "create table t_asset_attr_${m}${n}
	(
		flow_id   			bigint not null default 0 comment '流水ID',
		#通用信息
		loan_id            	bigint not null default 0 comment '标的ID',
		#标的属性部分
		scope_id				int not null default 0 comment '范畴ID,属于哪一个范畴,例如:标的运营属性,标的额外信息,2理财计划属性',
		scope_name			varchar(64) not null default '' comment '范畴名称',	
		section_id			int not null default 0 comment '借款项目基本信息|借款人基础信息|借款人资产信息|借款人工作信息||虚拟标的',
		section_name				varchar(64) not null default '' comment '例如：借款项目基本信息|借款人基础信息|借款人资产信息|借款人工作信息|借款人车辆信息|审核项目', 
		section_type      tinyint not null default 0 comment '状态：1-input框，2-checkbox框，3-其他',
		key_id				int not null default 0 comment 'key id',
		key_name					varchar(64) not null default '' comment 'key，例如:姓名|性别|年龄|学历|婚姻', 	
		#value_id				int not null default 0 comment 'value id',
		value					varchar(64) not null default '' comment 'value，例如：姓名|性别|年龄|学历|婚姻',
		
		isset				tinyint not null default 0 comment '业务类型是否设置了该属性，0-没有设置,1-设置',
		isnotnull			tinyint not null default 0 comment '0-非必填,1-必填',
		isdisplay       	tinyint not null default 0 comment '0-不向投资人展示,1-向投资人展示', 
	
		#有效性
		#如果理财计划保存了多个小散标,那么随着散标的到期以及补充新散标,那么理财计划可能会有记录历史的无效标
		invalidation			tinyint not null default 0 comment '散标ID是否有效,0表示有效也就是散标现在处于理财计划中,1表示无效也就是散标现在不在理财计划中',
		invalidation_reason	tinyint not null default 0 comment '0-正常,1-散标退出理财计划',
		#时间
		update_time        	datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time        	datetime not null default '0000-00-00 00:00:00' comment '创建时间',	
		primary key (loan_id,section_id,key_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的信息披露表,存放标的要披露的信息';"

	#标的表3
	#费用参数配置表
	#用来获取标的对应的费用参数配置
	$mysql_exec $db -e "create table t_asset_fee_${m}${n}
	(	
		flow_id   			bigint not null default 0 comment '流水ID',
		#通用信息
		loan_id            	bigint not null default 0 comment '标的ID',
		#费用配置部分
		fee_node				int not null default 0 comment	'收费节点:0-无,1-业务现场,2-放款前,3-发标前,4-还款时,5-借款结束时,6-违约发生时',
		fee_type 				int not null default 0 comment	'费用名称:0-无,1-平台服务费,2-代收咨询服务费,3-冻结首期投资人利息,4-进件服务费,5-保证金,6-GPS押金,7-GPS使用费,8-评估、查档、开户,9-抵押过户代办费,10-公证费,11-加急费,12-停车费,13-保险扣款,14-违章扣款,15-年检扣款',
		fee_comfire_type		int not null default 0 comment	'费用确认方式:0-无,1-固定费用，2-手动输入,3-按本金比例',
		fee_receiver			int not null default 0 comment 	'收费方：0-无，2-平台，3-进件方，4-担保公司，1-冻结 ',
		fee_standard			bigint not null default 0 comment '费用标准',
		fee_ispercent			tinyint not null default 0 comment '0-百分比,1-非百分比',
		fee_state				tinyint not null default 0 comment '0-未缴费,1-已缴费',
	
		#资金划扣方
		deduction_receiver		int not null default 0 comment 	'划扣方：0-无，1-平台，2-进件方，3-担保公司',
		#划扣节点				
		deduction_node			int not null default 0 comment	'划扣节点:0-无,1-放款时,2-逾期代偿,3-正常结清',
		#解冻节点  unfreeze_node
		unfrozen_node				int not null default 0 comment	'解冻节点:0-无,1-放款时,2-逾期代偿,3-正常结清',
		
		update_time			datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
		create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		primary key (loan_id,fee_node,fee_type,fee_receiver)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;"



	#标的表4
	#相关文件表
	#用来存放标的相关文件,讨论是否放到标的attr表中,例如
	$mysql_exec $db -e "create table t_asset_file_${m}${n}
	(	
		flow_id   			bigint auto_increment,
		#通用信息
		loan_id            	bigint not null default 0 comment '标的ID',
		#标的相关的文件信息
		file_name 			varchar(64) not null default '' comment '文件名',
		file_type 			tinyint not null default 0 COMMENT '0未知类型,1身份证认证,2结婚证,3户口本,4收入证明,5房产证,6车辆认证,7信用报告,8抵押认证,9其他',
		file_path 				varchar(512) not null default '' comment '提供访问文件的url地址',
		enabled 				tinyint not null default 0 comment '是否启用,启用表示在显示在前台网站,1为启用',
		create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		primary key (flow_id)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的文件表,用来存放标的相关的文件信息以及访问的url地址';"
		
done

#字典表：

$mysql_exec $db -e "create table t_asset_dict
(
	dict_id             int auto_increment,
	dict_name 			varchar(64) not null default '' comment '名称',
	dict_key			tinyint not null default 0  comment 'key',
	dict_value 			varchar(64) not null default '' COMMENT 'key值',
	dict_category		varchar(64) not null default '' comment '类别',
	dict_module 		varchar(512) not null default '' comment '模块',
	enabled 			tinyint not null default 0 comment '0-不启用，1为启用',
	create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	primary key (dict_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';"


#收取节点 ， 3是不能变的
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收取节点',3,'初审后','fee_node','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收取节点',4,'放款时','fee_node','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收取节点',5,'正常还款时','fee_node','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收取节点',6,'逾期还款时','fee_node','assetadminweb',1,now());"

#费用名称
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',1,'履约保证金','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',2,'融资服务费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',3,'融资服务费_先本后息','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',4,'融资服务费_等额本息','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',5,'咨询服务费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',6,'咨询服务费_先本后息','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',7,'咨询服务费_等额本息','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',8,'咨询服务费_有担保','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',9,'咨询服务费_无担保','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',10,'贷后管理费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',11,'担保费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',12,'客服服务费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',13,'还款保证金','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',14,'GPS押金','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',15,'GPS使用费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',16,'GPS折旧费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',17,'评估、查档、开户','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',18,'抵押过户代办费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',19,'公证费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',20,'加急费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',21,'停车费','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',22,'保险扣款','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',23,'违章扣款','fee_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用名称',24,'年检扣款','fee_type','assetadminweb',1,now());"


#费用确认方式
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用确认方式',1,'手动输入','fee_comfire_type','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('费用确认方式',2,'按本金比例','fee_comfire_type','assetadminweb',1,now());"
#fee_comfire_type_3 为 fee_comfire_type_dict_key
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('按比例',1,'按比例','fee_comfire_type_2','assetadminweb',1,now());"

#收费方
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收费方',1,'平台','fee_receiver','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收费方',2,'进件方','fee_receiver','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('收费方',3,'担保公司','fee_receiver','assetadminweb',1,now());"


#解冻节点（后面修改为返还节点） unfreeze_node		'解冻节点:0-无,1-放款时,2-逾期代偿,3-正常结清',
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('解冻节点',1,'取消合同时','unfreeze_node','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('解冻节点',2,'放款时','unfreeze_node','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('解冻节点',3,'逾期代偿','unfreeze_node','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('解冻节点',4,'结清时','unfreeze_node','assetadminweb',1,now());"

#还款方式 repayment_style
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('还款方式',1,'等额本息','repayment_style','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('还款方式',2,'等本等息','repayment_style','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('还款方式',3,'等额本金','repayment_style','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('还款方式',4,'先息后本','repayment_style','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('还款方式',5,'一次性还本付息','repayment_style','assetadminweb',1,now());"

#借款方式 borrow_style
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('借款方式',1,'信用借款','borrow_style','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('借款方式',2,'抵押借款','borrow_style','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('借款方式',3,'担保借款','borrow_style','assetadminweb',1,now());"

#进件方 incoming_party
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('进件方',1,'中业普惠','incoming_party','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('进件方',2,'中业正川','incoming_party','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('进件方',3,'中业众信','incoming_party','assetadminweb',1,now());"

$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('进件方',4,'遵明担保','incoming_party','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('进件方',5,'华敏担保','incoming_party','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('进件方',6,'融耀担保','incoming_party','assetadminweb',1,now());"

#担保方 guarantor
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('担保方',1,'遵明担保','guarantor','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('担保方',2,'华敏担保','guarantor','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('担保方',3,'融耀担保','guarantor','assetadminweb',1,now());"

$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('担保方',4,'中业普惠','guarantor','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('担保方',5,'中业正川','guarantor','assetadminweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('担保方',6,'中业众信','guarantor','assetadminweb',1,now());"


#业务类型 busitype
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',0,'提现手续费','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',1,'投资','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',2,'PAY_LOAN','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',3,'偿还','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',4,'收益','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',5,'充值','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',6,'提现','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',7,'CREATE','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',8,'SETTLE','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',9,'服务费保证金其它费','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',10,'垫付','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',11,'REC_FEE','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',12,'冻结','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',13,'解冻','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',14,'REC_PROFIT','busitype','accountweb',1,now());"
$mysql_exec $db -e "insert into  t_asset_dict(dict_name,dict_key,dict_value,dict_category,dict_module,enabled,create_time) values ('业务类型',15,'转账','busitype','accountweb',1,now());"



#理财计划
$mysql_exec $db -e "create table t_plan_base
(

	business_type		bigint not null default 0 comment '计划类型(业务类型ID)',
	business_name		varchar(64) not null comment '计划类型名称(业务类型名称)',
	
	#投标信息
	min_tender			bigint not null default 0 comment '投标时对投资金额的限制,最低允许金额（分）',
	increase_tender		bigint not null default 0 comment '递增金额,例如:加入金额10,000元起，且为10,000元的整数倍递增',
	max_tender			bigint not null default 0 comment '投标时对投资金额的限制,累计最高允许金额（分）',	
	
	repay_mode		 	int not null default 0 comment '收益方式：1:到期本息',	
	phase_mode			int not null default 0 comment '期数类型 1天 2月  3年',
	borrower_time		int not null default 0 comment '计划期限(借款期限)',
	
	bid_day 			int not null default 0  comment '募集期:天',
	#提前退出, 不能提前退出锁定期就很长
	lock_day			int not null default 0  comment '锁定期:不能提前退出锁定期就很长',
	enabled 			tinyint not null default 0 comment '0-不启用，1为启用',
	create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	update_time			datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
	primary key (business_type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='务类型表中的基本信息';"


#还款信息总表
$mysql_exec $db -e "create table ${db}.t_repayment
	(
		#通用信息
		repayment_id   			bigint not null default 0 comment '还款计划ID',
		asset_id        		bigint not null default 0 comment '标的id',   
		asset_type				tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_name          	varchar(64) not null comment '产品标题',
		annual_rate        		bigint not null default 0 comment '年化利率,万分之几',
		#借款人信息
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
		guarantee_date			datetime not null default '0000-00-00 00:00:00' comment '本期担保公司垫付日期',
		guarantee_gains 		bigint not null default 0 comment '本期担保公司因垫付获取的收益',
		guarantee_gains_date	datetime not null default '0000-00-00 00:00:00' comment '本期担保公司获取收益的日期',
		#借款人实际还款信息
		actual_principal 		bigint not null default 0 comment '本期实际已还本金',
		actual_interest  		bigint not null default 0 comment '本期实际已还利息',
		actual_penalty   		bigint not null default 0 comment '本期实际已缴罚金',	
		actual_compensation		bigint not null default 0 comment '本期实际已缴违约金',
		actual_pay_guarantee	bigint not null default 0 comment '本期垫付担保商的费率',
		actual_pay_incoming		bigint not null default 0 comment '本期垫付进件方的费率',
		actual_pay_platform		bigint not null default 0 comment '本期垫付给平台的费率',
		actual_date				datetime not null default '0000-00-00 00:00:00' comment '实际清偿日期',
		#还款计划的状态信息
		state         		int not null default 0 comment '状态：1-待还，2-已还,3-担保公司垫付,4-平台垫付,5-坏账',
		properties        	int not null default 0 comment '属性位：1-逾期，2-提前还款',
		#时间信息
		update_time       	datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	    primary key (repayment_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"	
	

$mysql_exec $db -e "create table t_investment
	(
		#通用信息
		financial_plan_id			bigint not null default 0 comment '理财计划ID',
		investment_id   			bigint not null default 0 comment '投资记录ID',
		financial_plan_investment_id   		bigint not null default 0 comment '理财计划对应的投资记录ID',
		asset_id            		bigint not null default 0 comment '标的ID',	 
		asset_type					tinyint not null default 0 comment '标的的类型:散标,理财计划虚拟标',
		asset_state					tinyint not null default 0 comment '标的当前状态:主要是判断散标是否已经结束',
		asset_name					varchar(64) not null default '' comment '资产名称',
		annual_rate       	 		int not null default 0 comment '年化利率,单位万分之一',
		phase_total        			int not null default 0 comment '借款期数',
		phase_mode					int not null default 0 comment '期数类型 0月 1天 2',
		repay_mode		 			int not null default 0 comment '还款方式：1先息后本 2等额平息 3等额本息 4等额本金 5一次性',	
		contract_no					varchar(64) not null default 0 comment '借款合同编号',
		#债权
		debt_id						bigint not null default 0 comment '债权ID',
		debt_name					varchar(64) not null default 0 comment '债权名称',
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
		received_principal			bigint not null default 0 comment '已收到的本金',
		received_interest			bigint not null default 0 comment '已收到的利息',
		received_money				bigint not null default 0 comment '已赚钱金额',
		next_payoff_day				datetime not null default '0000-00-00 00:00:00' comment '下一个回款日',
		#债权转让:
		lock_day					int not null default 0 comment '债权转让的锁定期',
		#时间信息
		update_time        			datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		create_time        			datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		finish_time					datetime not null default '0000-00-00 00:00:00' comment '结清时间',
		full_time					datetime not null default '0000-00-00 00:00:00' comment '标的满标时间',
		primary key (financial_plan_id,investment_id,asset_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
