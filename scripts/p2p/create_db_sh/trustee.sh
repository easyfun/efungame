db="trustee"
mysql_exec="mysql -uroot -p123456"
m=0
n=0

$mysql_exec -e "create database trustee"

for i in {0..99}
do
    l=$(($i/100))
    m=$(($i/10))
    n=$(($i%10))
    # 提现单表
    $mysql_exec $db -e "create table t_trustee_withdraw_order_${l}${m}${n}
    (
        order_id           bigint not null default 0 comment '提现单号',
        acc_id             bigint not null default 0 comment '账户UID',
        user_id            bigint not null default 0 comment '用户UID',
        amount             bigint not null default 0 comment '提现金额',
        bus_type           tinyint not null default 0 comment '业务类型',
        fee_mode			  tinyint not null default 0 comment '费率扣除方式，0-平台，1-用户',
        fee				  bigint not null default 0 comment '手续费',
        expired			  datetime not null default '0000-00-00 00:00:00' comment '过期时间',
        withdraw_type	  tinyint not null default 0 comment '提现方式，0-普通提现，1-加急提现',
        t0_fee			  bigint not null default 0 comment 't0 提现费用',
        loan_id            bigint not null default 0 comment '标的ID',
        free_id            bigint not null default 0 comment '冻结单号',
        remark             varchar(64) not null default '' comment '描述',
        channel_code	  	  varchar(64) not null default '' comment '提现来源',
        platform			  int not null default '0' comment '充值平台，0-PC,1-ANDROID，2-IOS，2-WEB，3-WeiXin,4-WAP',
        trans_id           bigint not null default 0 comment '导致该流水的业务交易ID',
        channel			  int not null default '0' comment '通道，0-易宝，1-富友',
        state              tinyint not null default 0 comment '状态：0-创建，1-提现申请成功,2-失败，3-打款中，4-打款成功，5-打款失败(退票)',
        update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
        create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
        primary key (order_id),
        unique key i_transid_btype (trans_id,bus_type)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
    # 提现流水表
    $mysql_exec $db -e "create table t_trustee_withdraw_flow_${l}${m}${n}
    (
        flow_id            bigint not null default 0 comment '流水',
        order_id           bigint not null default 0 comment '提现单号',
        acc_id             bigint not null default 0 comment '账户UID',
	   user_id            bigint not null default 0 comment '用户UID',
	   trans_id           bigint not null default 0 comment '事务UID',
	   amount             bigint not null default 0 comment '提现金额',
	   bus_type           tinyint not null default 0 comment '业务类型：1-用户提现，2-放款',
	   loan_id            bigint not null default 0 comment '产品id',
	   channel			  int not null default '0' comment '0-易宝，1-富友，',
	   free_id            bigint not null default 0 comment '冻结单号',
	   remark             varchar(64) not null default '' comment '描述',
	   state			  tinyint not null default 0 comment '状态：0-创建，1-提现申请成功,2-失败，3-打款中，4-打款成功，5-打款失败(退票)',
	   create_time 	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (flow_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	# 充值单表
	$mysql_exec $db -e "create table t_trustee_recharge_order_${l}${m}${n}
	(
	   order_id           bigint not null default 0 comment '充值单号',
	   user_id            bigint not null default 0 comment '用户UID',
	   acc_id             bigint not null default '0' comment '账户ID',
	   amount             bigint not null default 0 comment '充值金额',
	   fee_mode			  tinyint not null default 0 comment '费率扣除方式，0-平台，1-用户',
	   fee				  bigint not null default 0 comment '手续费',
	   pay_type			  tinyint not null default 0 comment '支付方式，，0-B2C网银，1-快捷支付,2-B2B网银',
	   bank_code		  varchar(30) not null default '' comment '网银银行代码',
	   state              tinyint not null default 0 comment '状态：0-创建，1-充值成功，2-充值失败',
	   remark             varchar(64) not null default '' comment '描述',
	   channel_code	 	  varchar(64) not null default '' comment '充值渠道来源',
	   platform			  int not null default '0' comment '充值平台，0-PC,1-ANDROID，2-IOS，2-WEB，3-WeiXin,4-WAP',
	   trans_id           bigint not null default 0 comment '导致该流水的业务交易ID',
	   loan_id            bigint not null default '0' comment '标的id',
	   bus_type           int not null default 0 comment '业务类型,1-普通充值，2-投标，3-还款',
	   channel			  int not null default '0' comment '通道，0-易宝，1-富友',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	   primary key (order_id),
	   unique key i_transid_btype (trans_id,bus_type)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	# 充值流水表
	$mysql_exec $db -e "create table t_trustee_recharge_flow_${l}${m}${n}
	(
	   flow_id            bigint not null default 0 comment '流水',
	   trans_id           bigint not null default 0 comment '导致该流水的业务交易ID',
	   order_id           bigint not null default 0 comment '充值单号',
	   acc_id             bigint not null default '0' comment '账户ID',
	   user_id            bigint not null default 0 comment '用户UID',
	   amount             bigint not null default 0 comment '充值金额',
	   pay_type			  tinyint not null default 0 comment '支付方式，，0-网银，1-快捷支付',
	   bank_code		  varchar(30) not null default '' comment '网银银行代码',
	   bus_type           tinyint not null default 0 comment '业务类型',
	   operation          tinyint not null default 0 comment '操作类型',
	   remark             varchar(64) not null default '' comment '描述',
	   loan_id            bigint not null default '0' comment '标的id',
	   state              tinyint not null default 0 comment '状态：0-创建，1-充值成功，2-充值失败',
	   channel			  int not null default '0' comment '通道，0-易宝，1-富友',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (flow_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	# 绑卡表
	$mysql_exec $db -e "create table t_trustee_card_order_${l}${m}${n}
	(	   	  
	   trans_id           bigint not null default 0 comment '导致该流水的业务交易ID',
	   card_no			  varchar(100) not null default '' comment '卡号',
	   user_id            bigint not null default 0 comment '用户UID',
	   real_name		  varchar(10) not null default '' comment '真实姓名',
	   card_bank		  varchar(4) not null default '' comment '所属银行，0102 -中国工商银行，0103	-中国农业银行，0104-中国银行，0105-中国建设银行，0301-交通银行，0302-中信实业银行，0303	-中国光大银行，0304-华夏银行，0305-中国民生银行，0306-广东发展银行，0307-平安银行股份有限公司，0308-招商银行，0309-兴业银行，0310-上海浦东发展银行，0403-国家邮政局邮政储汇局',
	   card_province	  varchar(5) not null default '' comment '所属省编号',
	   card_city		  varchar(5) not null default '' comment '所属市编号',
	   card_bank_branch   varchar(100) not null default '' comment '支行',
	   remark             varchar(64) not null default '' comment '描述',
	   state 			  tinyint not null default 0 comment '状态，0-未验证，1-验证通过，2-已解绑',
	   t0_flag			  int not null default 0 comment 'T+0提现标志，0-未开通，1-开通，2-暂停，3取消',
	   channel			  int not null default '0' comment '通道，0-易宝，1-富友',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   update_time        datetime not null default '0000-00-00 00:00:00' comment '更新时间',
	   withdraw_count     int not null default '0' comment '当月提现笔数',
	   withdraw_time	  datetime not null default '0000-00-00 00:00:00' comment '提现时间',
	   primary key (trans_id),
	   UNIQUE KEY i_crdno_chanl (card_no,channel) USING BTREE
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	# 绑卡流水表
	$mysql_exec $db -e "create table t_trustee_card_flow_${l}${m}${n}
	(
	   flow_id            bigint not null default 0 comment '流水',
	   card_no			  varchar(100) not null default '' comment '卡号',
	   user_id            bigint not null default 0 comment '用户UID',
	   card_bank		  varchar(4) not null default '' comment '所属银行，0102 -中国工商银行，0103	-中国农业银行，0104-中国银行，0105-中国建设银行，0301-交通银行，0302-中信实业银行，0303	-中国光大银行，0304-华夏银行，0305-中国民生银行，0306-广东发展银行，0307-平安银行股份有限公司，0308-招商银行，0309-兴业银行，0310-上海浦东发展银行，0403-国家邮政局邮政储汇局',
	   card_province	  varchar(5) not null default '' comment '所属省编号',
	   card_city		  varchar(5) not null default '' comment '所属市编号',
	   card_bank_branch   varchar(100) not null default '' comment '支行',
	   channel			  int not null default '0' comment '通道，0-易宝，1-富友',
	   remark             varchar(64) not null default '' comment '描述',
	   state 			  tinyint not null default 0 comment '状态，0-未验证，1-验证通过，2-已解绑',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '创建时间',
	   primary key (flow_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	
	#请求流水
	$mysql_exec $db -e "create table t_trustee_request_flow_${l}${m}${n}
	(
		 flow_id bigint not null default 0 comment '流水', 
		 order_id bigint not null default 0 comment '请求流水号',
		 user_id	bigint not null default '0' comment '用户ID',
		 trans_id bigint not null default 0 comment '导致该流水的业务交易ID',
		 amount bigint not null default '0' comment '交易金额',
		 type int not null default 0 comment '平台业务类型：1-用户注册，2-充值，3-提现，4-转账，5-冻结，6-解冻，7-投资，8-放款，9-还款，10-债权转让，11-代偿',
		 channel int not null default 0 comment '通道类型，0-易宝，1-富友，2-账户',
		 channel_type int not null default 0 comment '通道业务类型，1-开户，2-充值，3-提现，4-转账，5-冻结，6-解冻，7-划拨，8-冻结付款到冻结',	
		 state tinyint not null default 0 comment '状态：0-创建，1-操作成功，2-失败',
		 remark text not null default '' comment '备注',
		 create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		 primary key (flow_id)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
	#请求单表
	$mysql_exec $db -e "create table t_trustee_request_order_${l}${m}${n}
	(
		 order_id bigint not null default 0 comment '请求流水号',
		 user_id  bigint not null default '0' comment '用户ID',
		 trans_id bigint not null default 0 comment '导致该流水的业务交易ID',
		 amount bigint not null default 0 comment '交易金额',
		 to_uid bigint not null default 0 comment '交易对方UID',
		 loan_id bigint not null default 0 comment '标的ID',
		 type int not null default 0 comment '平台业务类型：0-用户注册，1-充值，2-提现，3-转账，4-冻结，5-解冻，6-投资，7-放款，8-还款，9-债权转让，10-代偿,11-冻结到冻结,12-放款费用',
		 channel int not null default 0 comment '通道类型，0-易宝，1-富友，2-账户',
		 channel_type int not null default 0 comment '通道业务类型，0-开户，1-充值，2-提现，3-转账，4-冻结，5-解冻，6-划拨，7-冻结付款到冻结',
		 request_url text not null comment '请求地址',
		 request_param text not null comment '请求参数',
		 response text not null comment '返回结果',
		 notify text not null comment '通知结果',
		 state tinyint not null default 0 comment '状态：0-创建，1-操作成功，2-失败',
		 remark text not null comment '备注',
		 create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
		 update_time datetime not null default '0000-00-00 00:00:00' comment '更新时间',
		 primary key (order_id)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
done

$mysql_exec $db -e "CREATE TABLE t_delay_repayment 
(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  trans_id bigint(20) NOT NULL DEFAULT '0' COMMENT '交易流水ID',
  from_uid bigint(20) NOT NULL DEFAULT '0' COMMENT '转出人',
  to_uid bigint(20) NOT NULL DEFAULT '0' COMMENT '转入人uid',
  loan_id bigint(20) DEFAULT NULL COMMENT '标的ID',
  amount bigint(20) NOT NULL DEFAULT '0' COMMENT '转账金额',
  bus_type int not null default '0' comment '业务类型',
  formfreeze tinyint(4) DEFAULT '0' COMMENT '原账户是否冻结，0未冻结，1冻结',
  tofreeze tinyint(11) DEFAULT '0' COMMENT '是否转入冻结，0不冻结，1冻结',
  state int(11) NOT NULL DEFAULT '0' COMMENT '状态，0未处理，1处理成功，2处理失败',
  remark varchar(32) DEFAULT NULL COMMENT '描述信息',
  create_time datetime DEFAULT NULL,
  update_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='延迟转账,富友开户后执行';"


$mysql_exec $db -e "CREATE TABLE t_err_code 
(
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  err_code varchar(32) NOT NULL,
  err_msg varchar(128) not null DEFAULT '',
  msg_show varchar(128) not null DEFAULT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('217', '0000', '交易成功', '交易成功');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('218', '1000', '取系统跟踪号失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('219', '1001', '无此用户', '无此用户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('220', '1002', '用户未激活', '用户未激活');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('221', '1003', '用户已锁定', '用户已锁定');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('222', '1004', '用户已关闭', '用户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('223', '1005', '用户已禁用', '用户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('224', '1006', '未知的用户类型', '用户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('225', '1007', '用户未指定', '未知用户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('226', '1008', '实名信息不合法', '实名信息不正确');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('227', '1009', '取授权号失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('228', '1014', '无效卡号(无此卡号)', '无效卡号(无此卡号)');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('229', '1042', '无此账户', '无此账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('230', '1051', '资金不足', '资金不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('231', '1101', '无此商户', '无此商户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('232', '1102', '商户（或机构）已关闭', '商户已关闭');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('233', '1103', '商户已锁定', '商户已锁定');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('234', '2000', '账户状态正常', '正常账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('235', '2001', '无此账户', '无此账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('236', '2002', '账户未激活', '账户未激活');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('237', '2003', '账户已锁定', '账户已锁定');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('238', '2004', '账户已冻结', '账户已冻结');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('239', '2005', '账户已销户', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('240', '2006', '账户已过期', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('241', '2007', '账户已挂失', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('242', '2008', '账户状态不正常', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('243', '2010', '分户状态正常', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('244', '2011', '找不到分户', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('245', '2012', '分户未激活', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('246', '2013', '分户已锁定', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('247', '2014', '分户已冻结', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('248', '2015', '分户已销户', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('249', '2016', '分户已过期', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('250', '2017', '分户已挂失', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('251', '2018', '分户状态不正常', '账户状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('252', '2019', '账户币种与交易币种不符', '交易币种不符');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('253', '2020', '未指定分户', '暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('254', '2030', '贷记账户状态正常', '贷记账户状态正常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('255', '2031', '无此贷记账户', '无此贷记账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('256', '2032', '贷记账户未激活', '贷记账户未激活');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('257', '2033', '贷记账户已锁定', '贷记账户已锁定');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('258', '2034', '贷记账户已冻结', '贷记账户已冻结');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('259', '2035', '贷记账户已销户', '贷记账户已销户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('260', '2036', '贷记账户已过期', '贷记账户已过期');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('261', '2037', '贷记账户已挂失', '贷记账户已挂失');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('262', '2038', '贷记账户状态不正常', '贷记账户状态不正常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('263', '2039', '借贷记账记不属于同一个机构', '借贷记账记不属于同一个机构');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('264', '2040', '借记账户不属于发起交易的机构', '怀疑作弊');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('265', '2041', '贷记账户不属于发起交易的机构', '怀疑作弊');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('266', '2101', '账号长度非法', '怀疑作弊');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('267', '2102', '账号检验失败', '怀疑作弊');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('268', '2103', '账户查询密码未设置', '安全验证错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('269', '2104', '账户支付密码未设置', '安全验证错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('270', '2105', '查询密码错误', '查询密码错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('271', '2106', '支付密码错误', '支付密码错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('272', '2107', '查询密码错误次数超限', '查询密码错误次数超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('273', '2108', '支付密码错误次数超限', '支付密码错误次数超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('274', '2109', '未知的密码类型', '格式错');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('275', '3001', '功能暂不支持', '功能暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('276', '3002', '未知的交易代码', '功能暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('277', '3003', '验证MAC失败', '安全验证错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('278', '3004', '不支持的交易', '功能暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('279', '3005', '禁止账户使用的交易', '功能暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('280', '3006', '禁止连接机构进行的交易', '功能暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('281', '3007', '无法确定交易账户的分户', '功能暂不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('282', '3011', '网关类交易金额超过上限', '交易金额太大');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('283', '3012', '金额无效', '金额无效');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('284', '3013', '账户未转结余额小于0', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('285', '3014', '未转结余额不足（小于交易金额）', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('286', '3015', '账户冻结余额小于0', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('287', '3016', '已冻结余额不足（小于交易金额）', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('288', '3017', '可用余额小于0', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('289', '3018', '可用余额不足', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('290', '3019', '交易金额小于下限', '交易金额太小');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('291', '3020', '账户总余额不为0', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('292', '3021', '账户总余额不足', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('293', '3022', '交易金额不足以支付手续费', '交易金额太小');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('294', '3023', '账户可用余额小于「交易金额+手续费」', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('295', '3024', '作业类交易金额超过上限', '金额太大');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('296', '3101', '找不到原交易(或原交易不成功)', '找不到原交易');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('297', '3102', '原交易不成功', '原交易不成功');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('298', '3103', '原交易已冲正', '原交易已冲正');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('299', '3104', '原交易已撤消', '原交易已撤消');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('300', '3105', '原交易已完成', '原交易已完成');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('301', '3106', '原交易已冻结', '原交易已冻结');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('302', '3107', '原交易已解冻', '原交易已解冻');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('303', '3108', '原交易金额不符', '原交易金额不符');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('304', '3109', '原交易账号不符', '原交易账号不符');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('305', '3110', '找不到原始授权交易', '找不到原始授权交易');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('306', '3111', '原交易清算日期非法', '原交易状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('307', '3112', '原交易不是清算交易', '原交易状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('308', '3120', '不需要记授权历史记录（参数错误）', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('309', '3121', '与原授权交易商户不匹配', '商户不匹配');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('310', '3122', '原授权交易已全部完成', '交易已完成');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('311', '3123', '交易卡号不匹配', '卡号不匹配');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('312', '3124', '交易金额超过可完成（解冻/撤消）的金额', '金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('313', '3125', '交易金额与原授权金额不一致', '金额不一致');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('314', '3126', '终端号不一致', '终端号不一致');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('315', '3127', '不在同一清算日内', '隔日交易，无法完成');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('316', '3201', '清分记账失败', '记账失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('317', '3251', '提现账户未指定', '提现账户未指定');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('318', '3252', '找商户附加信息错', '商户信息不完整');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('319', '3253', '提现账户用法错误(24域)', '格式错');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('320', '3271', '清分超时', '交易超时');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('321', '3272', '记账超时', '交易超时');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('322', '4001', '找不到指定的账户产品', '不允许开此账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('323', '4002', '不允许开户机构使用的账户产品', '不允许开此账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('324', '4003', '找不到指定账户产品的BIN号', '不支持的银行卡');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('325', '4004', '找不到指定科目的BIN号（或该科目不允许开分户）', '不允许开此账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('326', '4005', '某一BIN号的账户号资源已用完', '不允许开此账户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('327', '4006', '记客户账号表出错', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('328', '4007', '记客户内部账号表出错', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('329', '4008', '记基本账户表出错', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('330', '4009', '账户产品已过期，不能使用', '不支持的开户方式');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('331', '4010', '企业用户不能开对私账户', '不支持的开户方式');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('332', '4011', '个人用户不能开对公账户', '不支持的开户方式');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('333', '4012', '记客户账户密码失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('334', '4013', '个人客户账户已存在', '不可重复开户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('335', '5001', 'session超时', 'session超时');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('336', '5002', '验签失败', '验签失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('337', '5013', '无此交易权限', '无此交易权限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('338', '5017', '修改用户信息时未做任何修改', '未做任何修改');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('339', '5018', '根据地区代码和行别找不到对应支行', '根据地区代码和行别找不到对应支行');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('340', '5019', '数据校验失败', '数据校验失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('341', '5029', '调用交易查询接口过于频繁', '调用交易查询接口过于频繁');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('342', '5098', '订单支付失败，通讯异常', '订单支付失败，通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('343', '5100', '用户会话已失效请在半小时内及时完成订单', '用户会话已失效请在半小时内及时完成订单');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('344', '5101', '支付渠道选择有误支付渠道选择有误', '支付渠道选择有误支付渠道选择有误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('345', '5102', '目标机构配置有误目标机构配置有误', '目标机构配置有误目标机构配置有误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('346', '5110', '用户名或密码错误', '用户名或密码错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('347', '5119', '订单号重复', '订单号重复');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('348', '5137', '账户信息不能修改', '账户信息不能修改');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('349', '5123', '订单重复支付请不要重复提交订单', '订单重复支付请不要重复提交订单');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('350', '5127', '订单前后金额不一致订单前后金额不一致，金额可能被恶意篡改', '订单前后金额不一致订单前后金额不一致，金额可能被恶意篡改');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('351', '5128', '订单尚未完成付款', '订单尚未完成付款');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('352', '5138', '系统异常', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('353', '5143', '验证码错误', '验证码错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('354', '5183', '商户手续费不足', '商户手续费不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('355', '5239', '商户不存在', '商户不存在');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('356', '5336', '原交易找不到', '原交易找不到');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('357', '5343', '用户已开户', '用户已开户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('358', '5344', '账务系统开户失败', '账务系统开户失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('359', '5345', '商户流水号重复', '商户流水号重复');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('360', '5346', '商户流水号不存在', '商户流水号不存在');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('361', '5347', '与商户系统日期不一致', '与商户系统日期不一致');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('362', '5348', '交易用户不存在', '交易用户不存在');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('363', '5349', '找不到原交易', '找不到原交易');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('364', '5350', '指令提交模式只支持富友余额支付', '指令提交模式只支持富友余额支付');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('365', '5351', '商户提现流水号重复', '商户提现流水号重复');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('366', '5352', '未找到该商户交易记录', '未找到该商户交易记录');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('367', '5353', '接收FAS报文出现异常', '接收FAS报文出现异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('368', '5354', 'FAS报文验签失败', 'FAS报文验签失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('369', '5355', '发送FAS通讯出现异常', '发送FAS通讯出现异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('370', '5356', '该卡号已认证', '该卡号已认证');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('371', '5357', '该卡号已经受理且认证通过', '该卡号已经受理且认证通过');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('372', '5358', '该卡号已经受理,但尚未认证通过', '该卡号已经受理,但尚未认证通过');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('373', '5359', '该卡号尚未签约', '该卡号尚未签约');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('374', '5360', '银行卡已签约', '银行卡已签约');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('375', '5460', '发送日切通知失败', '发送日切通知失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('376', '5505', '不支持的银行卡\n', '不支持的银行卡\n');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('377', '5555', '交易确定超时', '交易确定超时');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('378', '5556', '用户信息修改期间不能代扣充值或提现', '用户信息修改期间不能代扣充值或提现');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('379', '5557', '用户未授权', '用户未授权');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('380', '5836', '不允许信用卡注册', '不允许信用卡注册');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('381', '5837', '卡号和行别不一致', '卡号和行别不一致');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('382', '5850', '已经存在相同银行卡号注册的用户', '已经存在相同银行卡号注册的用户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('383', '5851', '已经存在相同证件号注册的用户', '已经存在相同证件号注册的用户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('384', '5852', '实名验证失败', '实名验证失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('385', '5853', '商户IP不允许访问', '商户IP不允许访问');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('386', '5854', '协议库验证日期超过7天', '协议库验证日期超过7天');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('387', '5855', '该手机号绑定卡号超过2张', '该手机号绑定卡号超过2张(在代收付系统解约)');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('388', '5856', '无权限访问该接口', '无权限访问该接口');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('389', '5857', '实名验证失败,当日总验证次数超限', '验证次数超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('390', '5891', '用户已开户', '用户已开户');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('391', '5901', '查询范围只允许在31填内或31天前', '查询范围只允许在31填内或31天前');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('392', '5996', '抱歉，该交易不被允许', '抱歉，该交易不被允许');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('393', '6901', '单笔金额超限单笔金额超限', '单笔金额超限单笔金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('394', '6902\n6902', '当日累计金额超限当日累计金额超限', '当日累计金额超限当日累计金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('395', '8010', '订单支付失败，获取验证码失败', '订单支付失败，获取验证码失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('396', '8143', '订单支付失败，验证码失效或错误', '订单支付失败，验证码失效或错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('397', '9000', '未知的操作方式，参数错误', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('398', '9001', '未知的取流水号方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('399', '9002', '找不到连接机构', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('400', '9003', '连接机构已关闭', '机构已关闭');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('401', '9004', '连接机构非法', '机构状态异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('402', '9005', '未知的查找原交易方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('403', '9006', '未知的余额检查类型', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('404', '9007', '未知的交易日志更新方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('405', '9008', '未知的授权历史日志更新方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('406', '9009', '未知的原交易日志更新方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('407', '9010', '未知的原授权历史日志更新方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('408', '9011', '未知的原始交易数据用法', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('409', '9012', '未知的账户资料更新方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('410', '9013', '未知的授权历史日志检查方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('411', '9014', '未知的原交易更新对象', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('412', '9015', '未知的账户状态检查方式', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('413', '9016', '系统异常', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('414', '9701', '内存错误', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('415', '9801', '数据库错误', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('416', '9802', '提交数据库事务失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('417', '9803', '数据库SELECT操作失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('418', '9804', '数据库INSERT操作失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('419', '9805', '数据库UPDATE操作失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('420', '9806', '数据库事务回滚失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('421', '9901', 'SOTP同步调用失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('422', '9902', 'SOTP异步调用失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('423', '10029', '金额超限', '金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('424', '55137', '证件类型为空或者内容不正确', '证件类型为空或者内容不正确');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('425', '100011', '卡号或者户名不符', '卡号或者户名不符');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('426', '100016', '金额超限', '金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('427', '100017', '余额不足', '余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('428', '100029', '金额超限', '金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('429', '100042', '订单支付失败，手机号不符', '订单支付失败，手机号不符');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('430', '100044', '密码输入错误次数超限', '密码输入错误次数超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('431', '210001', '发送kbpsDataBean失败', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('432', '210002', 'kbps没有初始化，无法连接', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('433', '100012', '证件号码不匹配', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题。');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('434', '100012', '证件号码不符', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('435', '100012', '客户证件类型或证件号码不符', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('436', '100013', '账户不允许交易', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('437', '100013', '持卡人身份信息或手机号输入不正确，验证失败[10000P1]', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('438', '100013', '持卡人身份信息或手机号输入不正确，验证失败[1000005]', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('439', '100013', '不匹配', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('440', '999998', '认证失败', '银行卡信息有误，验证失败。信息有误的情况下，大部分银行为防止套银行卡信息，不会返回具体是什么要素有问题');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('441', '1004', '无签约信息，不能发起扣款', '无签约信息，不能发起扣款');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('442', '1005', '暂不支持的银行卡', '暂不支持的银行卡');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('443', '1008', '短信发送过于频繁，请稍后再试', '短信发送过于频繁，请稍后再试');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('444', '1010', '错误次数超限，请核对卡信息稍后再试', '错误次数超限，请核对卡信息稍后再试');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('445', '1040', '请求的功能尚不支持', '请求的功能尚不支持');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('446', '5185', '订单已支付', '订单已支付');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('447', '8110', '验证码验证次数超限', '验证码验证次数超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('448', '9999', '证件号必须为15或者18位', '证件号必须为15或者18位');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('449', '0001', '发送失败', '发送失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('450', '0020', '手机号信息非法', '手机号信息非法');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('451', '0021', '订单号重复', '订单号重复');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('452', '0030', '报文内容信息非法', '报文内容信息非法');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('453', '10FE', '贷记卡单笔交易金额超限', '贷记卡单笔交易金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('454', '10M1', '超出借记卡同商户单日交易累计金额限额', '超出借记卡同商户单日交易累计金额限额');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('455', '10M2', '超出借记卡同商户当月累计金额限制', '超出借记卡同商户当月累计金额限制');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('456', '10SM', '超过金额限制', '超过金额限制');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('457', '51B3', '订单支付中，请勿重复支付', '订单支付中，请勿重复支付');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('458', '1042', '转入卡号或户名错误', '转入卡号或户名错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('459', '10XC        ', '户名或证件号码不符', '户名或证件号码不符');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('460', '8010', '订单支付失败，获取验证码失败', '获取验证码失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('461', '1051', '余额不足', '银行卡余额小于付款金额，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('462', '1061', '超出取款/转账金额限制', '银行卡设置了取款/转账限额，限额小于付款金额，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('463', '1096', '系统异常、失效', '银行系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('464', '10FC        ', '借记卡单笔交易金额超限', '单笔付款金额小于富友设置的最低交易限额，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('465', '10FD', '同卡金额超限', '同卡金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('466', '10FB', '同卡笔数超限', '同卡笔数超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('467', '10HA', '商户电子凭条未经审核', '商户电子凭条未经审核');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('468', '10M2        ', '超出借记卡同商户当月累计金额限制', '累计付款金额大于富友设置的月交易限额上限，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('469', '10SM        ', '超过金额限制', '单笔付款金额大于富友设置的最高交易限额，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('470', '999998', '您的银行卡暂不支持该业务，请向您的银行或95516咨询[1000040]', '银行卡未开通银联在线功能，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('471', '999998', '账户类型不支持', '银行卡未开通银联在线或其它通道所需开通的功能，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('472', '999999', '账户类型不支持', '银行卡未开通银联在线或其它通道所需开通的功能，交易失败');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('473', '5098', '系统处理中', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('474', '5098', '通讯异常', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('475', '5138', '系统异常', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('476', '999999', '通信异常接收报文异常', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('477', '999999', '交易状态未知', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('478', '999999', '交易通讯超时，请发起查询交易', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('479', '999999', '该交易维护中,请稍后再试', '银行系统或通讯异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('480', '200012', '报文格式错误', '报文格式错误');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('481', '200013', '持卡人身份信息或手机号与银行预留不一致，或未开通银联在线功能', '持卡人身份信息或手机号与银行预留不一致，或未开通银联在线功能');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('482', '200014', '银行卡未开通银联在线功能或为不支持的卡', '银行卡未开通银联在线功能或为不支持的卡');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('483', '200015', '卡状态不正常', '卡状态不正常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('484', '200016', '无效卡', '无效卡');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('485', '200017', '银行卡账户余额不足', '银行卡账户余额不足');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('486', '200023', '银联风险受限', '银联风险受限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('487', '200029', '交易金额超限', '交易金额超限');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('488', '200098', '交易超时', '交易超时');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('489', '999998', '交易失败，详情请咨询您的发卡行', '交易失败，详情请咨询您的发卡行');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('490', '999999', '系统异常', '系统异常');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('491', '999999', '交易通讯超时，请发起查询交易', '交易通讯超时，请发起查询交易');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('492', '999999', '交易状态未明，请查询对账结果', '交易状态未明，请查询对账结果(04Z3003)');"
$mysql_exec $db -e " INSERT INTO t_err_code VALUES ('493', '999999', '请求的功能尚不支持', '请求的功能尚不支持');"

