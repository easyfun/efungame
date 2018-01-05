#!/bin/bash
password=123456
mysql_exec="mysql -uroot -p$password"
db=user

$mysql_exec -e "create database $db;"
#用户信息表
$mysql_exec $db -e "create table t_user
(
    id bigint(20) NOT NULL COMMENT 'id',
    username varchar(64) NOT NULL COMMENT '用户名，唯一键',
    nickname varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
    password varchar(128) NOT NULL COMMENT '密码',
    mobile varchar(20) NOT NULL COMMENT '手机号',
    status int(11) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1锁定 2删除 3黑名单',
    reg_channel varchar(64) COMMENT '注册渠道号',
	real_reg_channel varchar(64)  COMMENT '真实注册渠道号',
    reg_platform int(11) NOT NULL DEFAULT '0' COMMENT '注册平台 0:PC 1:APP 2:WeiXin ',
    user_type int(11) NOT NULL DEFAULT '0' COMMENT '用户类型 0投资用户 1借款用户 2授权中心',
    reg_ip varchar(64) COMMENT '注册ip',
    reg_date datetime NOT NULL COMMENT '注册时间',
    referee varchar(64) DEFAULT NULL COMMENT '推荐人手机号',
    referee_id bigint(20) COMMENT '推荐人ID',
    referee_name varchar(20) DEFAULT NULL COMMENT '推荐人姓名',
    update_time datetime DEFAULT NULL COMMENT '最后一次修改时间',
    create_time datetime DEFAULT NULL COMMENT '创建时间',
    head_pic_url varchar(256) DEFAULT '' COMMENT '头像地址',
    PRIMARY KEY (id),
    UNIQUE KEY username (username),
    UNIQUE KEY mobile (mobile),
    KEY nickname (nickname),
    KEY password(password),
    KEY status (status),
    KEY reg_channel (reg_channel),
	KEY real_reg_channel (real_reg_channel),
    KEY regPlatform (reg_platform),
    KEY user_type (user_type),
    KEY referee (referee),
    KEY update_time(update_time),
    KEY referee_id (referee_id),
    KEY referee_name (referee_name),
    KEY reg_ip (reg_ip),
    KEY reg_date (reg_date),
    KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';"

#用户详细信息表
$mysql_exec $db -e "create table t_user_detail
(
		id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
		userid bigint(20) NOT NULL COMMENT '用户id',
		gender char(1) DEFAULT NULL COMMENT '性别',
		real_name varchar(40) DEFAULT NULL COMMENT '姓名',
		birthday datetime DEFAULT NULL COMMENT '生日',
		age int(11) DEFAULT '0' COMMENT '年龄',
		marriage int(11) DEFAULT '0' COMMENT '0未婚 1已婚 2保密',
		education varchar(10) DEFAULT '' COMMENT '学历',
		email varchar(200) DEFAULT NULL COMMENT '邮箱',
		id_card_no varchar(100) DEFAULT NULL COMMENT '证件号码',
		id_card_type int(11) DEFAULT NULL COMMENT '证件类型(0.未知类型 1.中国大陆居民身份证 2.台湾居民来往大陆通行证 3.港澳居民来往内地通行证)',
		id_card_status int(11) DEFAULT NULL COMMENT '实名状态 0未实名 1未认证 2已实名',
		security_level int(3) unsigned DEFAULT NULL COMMENT '账户安全等级',
		update_time datetime DEFAULT NULL COMMENT '最后一次修改时间',
		create_time datetime DEFAULT NULL COMMENT '创建时间',
		city_code varchar(30) DEFAULT '' COMMENT '城市code',
		pro_code varchar(30) DEFAULT '' COMMENT '省份code',
		profession varchar(64) DEFAULT '' COMMENT '专业',
		yb_identificat_time datetime DEFAULT NULL COMMENT '易宝实名时间',
		fy_identificat_time datetime DEFAULT NULL COMMENT '富友实名时间',
		PRIMARY KEY (id),
		KEY userid (userid),
		KEY real_name (real_name),
		KEY gender (gender),
		KEY age (age),
		KEY marriage (marriage),
		KEY education (education),
		KEY birthday (birthday),
		KEY email (email),
		KEY idcardNo (id_card_no),
		KEY idcardtype (id_card_type),
		KEY id_card_status (id_card_status),
		KEY security_level (security_level),
		KEY update_time (update_time),
		KEY city_code (city_code),
		KEY pro_code (pro_code),
		KEY profession (profession),
		KEY yb_identificat_time (yb_identificat_time),
		KEY fy_identificat_time (fy_identificat_time),
		KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息';"

#用户属性表
$mysql_exec $db -e "create table t_user_attr
(
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    userid bigint(20) NOT NULL COMMENT '用户id',
    name varchar(64) NOT NULL COMMENT '名称',
    value varchar(250) NOT NULL COMMENT '值',
    update_time datetime DEFAULT NULL COMMENT '最后一次修改时间',
    create_time datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY userid (userid),
    KEY name (name),
    KEY value (value),
    KEY update_time (update_time),
    KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户属性';"

#用户登录密码错误次数信息
$mysql_exec $db -e "create table t_user_login_error
(
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    userid bigint(20) NOT NULL COMMENT '用户id',
    times int(11) NOT NULL COMMENT '用户id',
    last_login_date datetime DEFAULT NULL COMMENT '最后一次登录时间',
    PRIMARY KEY (id),
    UNIQUE KEY userid (userid),
    KEY times (times),
    KEY last_login_date (last_login_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录密码错误次数信息';"


#用户登录历史
$mysql_exec $db -e "create table t_user_login_log
(
    id 			bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    userid  	bigint(20) not null comment '用户id',
    ip		    varchar(64)  default '' comment '登录ip',
    platform	int(11)   default 0 comment '注册平台 0:PC 1:APP 2:WeiXin ',
    channel		varchar(64)  default '' comment '注册渠道号',
    login_time			datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
    create_time       	datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    primary key(id),
    KEY userid (userid),
    KEY platform (platform),
    KEY login_time (login_time),
    KEY create_time (create_time),
    KEY channel (channel),
    KEY ip (ip)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#用户充值投资统计表
$mysql_exec $db -e "create table t_user_charge_invest_record
(
    id 			bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    userid  	bigint(20) not null comment '用户id',
    charge_num  int(11)   default 0 comment '充值次数',
    invest_num	int(11)   default 0 comment '投资次数',
    update_time	datetime not null default '0000-00-00 00:00:00' comment '上次修改时间',
    create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    PRIMARY KEY (id),
    KEY userid (userid),
    KEY charge_num (charge_num),
    KEY invest_num (invest_num),
    KEY update_time (update_time),
    KEY create_time (create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#老用户id与新用户id关联表
$mysql_exec $db -e "create table t_userid_rel_olduid
(
	userid bigint(20) NOT NULL,
	ybuid varchar(100) DEFAULT '',
	platform_user_no varchar(100) DEFAULT '',
	PRIMARY KEY (userid),
	KEY ybuid(ybuid),
	KEY platform_user_no(platform_user_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='老用户id与新用户id关联表';"

for i in {0..199}
do	
    m=$(($i/100))
    n=$(($i%100/10))
    l=$(($i%10))
	
    #用户表
    $mysql_exec $db -e "create table t_user_${m}${n}${l}
    (
        id bigint(20) NOT NULL COMMENT 'id',
        username varchar(64) NOT NULL COMMENT '用户名，唯一键',
        nickname varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
        password varchar(128) NOT NULL COMMENT '密码',
        mobile varchar(20) NOT NULL COMMENT '手机号',
        status int(11) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1锁定 2删除 3黑名单',
        reg_channel varchar(64)  COMMENT '注册渠道号',
		real_reg_channel varchar(64)  COMMENT '真实注册渠道号',
        reg_platform int(11) NOT NULL DEFAULT '0' COMMENT '注册平台 0:PC 1:APP 2:WeiXin ',
        user_type int(11) NOT NULL DEFAULT '0' COMMENT '用户类型 0投资用户 1借款用户 2授权中心',
        reg_ip varchar(64)  COMMENT '注册ip',
        reg_date datetime NOT NULL COMMENT '注册时间',
        referee varchar(64) DEFAULT NULL COMMENT '推荐人手机号',
        referee_id bigint(20)  COMMENT '推荐人ID',
        referee_name varchar(20) DEFAULT NULL COMMENT '推荐人姓名',
        update_time datetime DEFAULT NULL COMMENT '最后一次修改时间',
        create_time datetime DEFAULT NULL COMMENT '创建时间',
        head_pic_url varchar(256) DEFAULT '' COMMENT '头像地址',
        PRIMARY KEY (id),
        UNIQUE KEY username (username),
        UNIQUE KEY mobile (mobile),
        KEY nickname (nickname),
        KEY password(password),
        KEY status (status),
        KEY reg_channel (reg_channel),
		KEY real_reg_channel (real_reg_channel),
        KEY regPlatform (reg_platform),
        KEY user_type (user_type),
        KEY referee (referee),
        KEY update_time(update_time),
        KEY referee_id (referee_id),
        KEY referee_name (referee_name),
        KEY reg_ip (reg_ip),
        KEY reg_date (reg_date),
        KEY create_time (create_time)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';"

    #用户id与IDCARD关系映射表
    $mysql_exec $db -e "create table t_userid_idcard_rel_${m}${n}${l}
    (
        userid bigint(20) NOT NULL,
        id_card_no varchar(100) NOT NULL,
        id_card_type int(11) NOT NULL,
        status int(11) NOT NULL DEFAULT '0' COMMENT '0正常 1禁用',
        PRIMARY KEY (id_card_no,id_card_type),
        KEY id_card_no (id_card_no),
        KEY id_card_type (id_card_type),
        KEY userid (userid),
        KEY status (status)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户id与IDCARD关系映射表';"
	
	#用户id与mobile关系映射表
	$mysql_exec $db -e "create table t_userid_mobile_rel_${m}${n}${l}
	(
        userid bigint(20) NOT NULL,
        mobile varchar(20) NOT NULL,
        status int(11) NOT NULL DEFAULT '0' COMMENT '0正常 1禁用',
        UNIQUE KEY mobile (mobile),
        KEY userid(userid),
        KEY status(status)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户id与mobile关系映射表';"
	
	
	#用户登录密码错误次数信息
	$mysql_exec $db -e "create table t_user_login_error_${m}${n}${l}
	(
        id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
        userid bigint(20) NOT NULL COMMENT '用户id',
        times int(11) NOT NULL COMMENT '用户id',
        last_login_date datetime DEFAULT NULL COMMENT '最后一次登录时间',
        PRIMARY KEY (id),
        UNIQUE KEY userid (userid),
        KEY times (times),
        KEY last_login_date (last_login_date)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录密码错误次数信息';"\
	
	
	#用户id与username关系映射表
	$mysql_exec $db -e "create table t_userid_username_rel_${m}${n}${l}
	(
        userid bigint(20) NOT NULL,
        username varchar(64) NOT NULL,
        status int(11) NOT NULL DEFAULT '0' COMMENT '0正常 1禁用',
        UNIQUE KEY username (username),
          KEY userid(userid),
        KEY status(status)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户id与username关系映射表';"
	
	#用户属性表
	$mysql_exec $db -e "create table t_user_attr_${m}${n}${l}
(
        id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
        userid bigint(20) NOT NULL COMMENT '用户id',
        name varchar(64) NOT NULL COMMENT '名称',
        value varchar(250) NOT NULL COMMENT '值',
        update_time datetime DEFAULT NULL COMMENT '最后一次修改时间',
        create_time datetime DEFAULT NULL COMMENT '创建时间',
        PRIMARY KEY (id),
        KEY userid (userid),
        KEY name (name),
        KEY value (value),
        KEY update_time (update_time),
        KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户属性';"


#用户详细信息表
$mysql_exec $db -e "create table t_user_detail_${m}${n}${l}
	(
        id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
        userid bigint(20) NOT NULL COMMENT '用户id',
        gender char(1) DEFAULT NULL COMMENT '性别',
        real_name varchar(40) DEFAULT NULL COMMENT '姓名',
        birthday datetime DEFAULT NULL COMMENT '生日',
        age int(11) DEFAULT '0' COMMENT '年龄',
        marriage int(11) DEFAULT '0' COMMENT '0未婚 1已婚 2保密',
        education varchar(10) DEFAULT '' COMMENT '学历',
        email varchar(200) DEFAULT NULL COMMENT '邮箱',
        id_card_no varchar(100) DEFAULT NULL COMMENT '证件号码',
        id_card_type int(11) DEFAULT NULL COMMENT '证件类型(0.未知类型 1.中国大陆居民身份证 2.台湾居民来往大陆通行证 3.港澳居民来往内地通行证)',
        id_card_status int(11) DEFAULT NULL COMMENT '实名状态 0未实名 1未认证 2已实名',
        security_level int(3) unsigned DEFAULT NULL COMMENT '账户安全等级',
        update_time datetime DEFAULT NULL COMMENT '最后一次修改时间',
        create_time datetime DEFAULT NULL COMMENT '创建时间',
        city_code varchar(30) DEFAULT '' COMMENT '城市code',
        pro_code varchar(30) DEFAULT '' COMMENT '省份code',
        profession varchar(64) DEFAULT '' COMMENT '专业',
		yb_identificat_time datetime DEFAULT NULL COMMENT '易宝实名时间',
		fy_identificat_time datetime DEFAULT NULL COMMENT '富友实名时间',
        PRIMARY KEY (id),
        KEY userid (userid),
        KEY real_name (real_name),
        KEY gender (gender),
        KEY age (age),
        KEY marriage (marriage),
        KEY education (education),
        KEY birthday (birthday),
        KEY email (email),
        KEY idcardNo (id_card_no),
        KEY idcardtype (id_card_type),
        KEY id_card_status (id_card_status),
        KEY security_level (security_level),
        KEY update_time (update_time),
        KEY city_code (city_code),
        KEY pro_code (pro_code),
        KEY profession (profession),
        KEY yb_identificat_time (yb_identificat_time),
		KEY fy_identificat_time (fy_identificat_time),
        KEY create_time (create_time)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息';"
done
