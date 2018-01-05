#!/bin/bash
#################################################################
#password=123456
l=0
m=0
n=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "
db=message

$mysql_exec -e "create database ${db}"


$mysql_exec $db -e "CREATE TABLE t_sms_record (
  id           bigint(20)   NOT NULL AUTO_INCREMENT 		COMMENT 'id',
  mobiles      text          								COMMENT '手机号，多个用,分割',
  content      varchar(512) NOT NULL 						COMMENT '短信内容',
  totalcount   int 					DEFAULT 0 				COMMENT '手机号码总数',
  telnumber    int 					DEFAULT 0 				COMMENT '接受人号码的数量',
  smscount     int 					DEFAULT 0 				COMMENT '使用短信数量',
  status       tinyint      DEFAULT 0						COMMENT '状态0成功，1失败2不发送',
  msg          varchar(256) DEFAULT '' 						COMMENT '成功或者失败原因',
  provider     varchar(32)  DEFAULT '' 						COMMENT '短信供应商',
  smsid        varchar(32)  DEFAULT '' 						COMMENT '流水id',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (id),
  KEY status (status),
  KEY provider (provider),
  KEY smsid (smsid),
  KEY create_time (create_time)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='短信记录表';"


for i in {0..199}
do
	l=$(($i/100))
	m=$((($i-$l*100)/10))
	n=$(($i%10))

	# 用户验证码表
	$mysql_exec $db -e "create table t_user_verifycode_${l}${m}${n}
	(
		mobile       varchar(16)  NOT NULL       COMMENT '手机号',
		type         varchar(32)  DEFAULT ''     COMMENT '验证码类型，register注册，updatepassword修改密码，修改密码，提现',
		verifycode   varchar(16)  DEFAULT ''     COMMENT '验证码',
		verifytimes  int         DEFAULT 0       COMMENT '当前验证码验证次数' ,
		regettimes   int         DEFAULT 0       COMMENT '重新获取验证码次数' ,
		gettimes     int         DEFAULT 1       COMMENT '获取验证码总次数' ,
		status       tinyint      DEFAULT 0      COMMENT '状态0未使用 1已使用' ,
		clientip     varchar(64)  DEFAULT ''     COMMENT 'ip地址' ,
		lastgettime  datetime not null default '0000-00-00 00:00:00'    COMMENT '上次获取验证码的时间' ,
		create_time  datetime not null default '0000-00-00 00:00:00'    comment '创建时间',
		PRIMARY KEY (mobile),
		KEY type (type),
		KEY status (status),
		KEY create_time (create_time)
	) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='用户验证码表';"

done

$mysql_exec $db -e "CREATE TABLE t_sms_template (
  type         varchar(32)  DEFAULT '' 			COMMENT '类型，login登录 register注册，updatepassword修改密码，resetpassword重置密码，withdraw提现',
  content      varchar(512)  NOT NULL 			COMMENT '模板',
  status       tinyint      DEFAULT 0 			COMMENT '状态0正常 1不用' ,
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (type),
  KEY status (status)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='短信模板表';"

$mysql_exec -e "INSERT INTO message.t_sms_template VALUES ('login', '验证码：{code}，此验证码用于账户登录，切勿告知他人，如非本人操作请及时致电客服400-658-9898。', 0, now());
INSERT INTO message.t_sms_template VALUES ('recharge', '验证码：{code}，此验证码用于账户充值，切勿告知他人，如非本人操作请及时致电客服400-658-9898。', 0, now());
INSERT INTO message.t_sms_template VALUES ('register', '注册验证码：{code}，验证码有效期10分钟。如非本人操作，请勿理会。', 0, now());
INSERT INTO message.t_sms_template VALUES ('resetpassword', '验证码：{code}，此验证码用于账户密码重置，切勿告知他人，如非本人操作请及时致电客服400-658-9898。', 0, now());
INSERT INTO message.t_sms_template VALUES ('updatepassword', '验证码：{code}，此验证码用于账户密码修改，切勿告知他人，如非本人操作请及时致电客服400-658-9898。', 0, now());
INSERT INTO message.t_sms_template VALUES ('withdraw', '验证码：{code}，此验证码用于账户提现，切勿告知他人，如非本人操作请及时致电客服400-658-9898。', 0, now());"


