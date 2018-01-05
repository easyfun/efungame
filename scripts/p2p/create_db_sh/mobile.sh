#!/bin/bash
#################################################################
#password=123456
l=0
m=0
n=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "
db=mobile

$mysql_exec -e "create database ${db}"

$mysql_exec $db -e "CREATE TABLE t_mobile_api_error (
  id 			bigint NOT NULL AUTO_INCREMENT				COMMENT 'id',
  url 			varchar(250)  default ''					COMMENT 'url',
  value 		text 										COMMENT '值',
  useragent 	varchar(250)								COMMENT '用户代理',
  status 		tinyint default 0 							COMMENT '状态，0为新问题',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (id),
  key(url),
  key(status),
  key(create_time)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT '接口错误';"




$mysql_exec $db -e "CREATE TABLE t_mobile_login (
   id					bigint(20)  NOT NULL AUTO_INCREMENT,
   ostype               varchar(16) not null default ''		comment '系统类型 android,ios',
   channel              varchar(32) not null default ''		comment '渠道号',
   deviceid             varchar(64) not null default ''		comment '设备id,ios获取idfa，android获取imei',
   imsi					varchar(64) default '' 				comment '只是安卓获取imsi',
   mac                  varchar(64) default '' 				comment '只是安卓获取mac地址',
   phonetype            varchar(32) default ''           	comment '手机型号' ,
   phoneresolution      varchar(32) default ''            	comment '手机分辨率,格式为640*480',
   network              varchar(8)	default ''			    comment	'网络类型，2g,3g,4g,wifi',
   version              varchar(16)	default ''				comment	'客户端版本',
   latitude             decimal(15,8) default 0			comment '经纬度latitude',
   longitude            decimal(15,8) default 0			comment '经纬度longitude',
   systemversion        varchar(16)	 default ''				comment '系统版本',
   clientip             varchar(64)	 default ''				comment '客户端ip',
   userid               bigint(20)   not null    			COMMENT '用户id',
   mobile				varchar(20) 	DEFAULT '' 			COMMENT '电话号码',
   username				varchar(36) 	DEFAULT '' 			COMMENT '用户名',
   realname				varchar(64) 	DEFAULT '' 			COMMENT '真实姓名',
   openid				varchar(128)    default ''   		comment 'openid',
   openidtype			varchar(64)		default 'none'		comment 'openid类型,none为不使用',
   ext1                 varchar(64) DEFAULT '' 				COMMENT '保留',
   ext2                 varchar(64) DEFAULT '' 				COMMENT '保留',
   ext3                 varchar(64) DEFAULT '' 				COMMENT '保留',
   create_time          datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
   primary key (id),
   key(ostype),
   key(channel),
   key(deviceid),
   key(network),
   key(version),
   key(userid),
   key(mobile),
   key(username),
   key(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录统计';"



$mysql_exec $db -e "CREATE TABLE t_mobile_reg (
   id					bigint(20)NOT NULL AUTO_INCREMENT,
   ostype               varchar(16) not null default ''		comment '系统类型 android,ios',
   channel              varchar(32) not null default ''		comment '渠道号',
   deviceid             varchar(64) not null default ''		comment '设备id,ios获取idfa，android获取imei',
   imsi					varchar(64) default '' 				comment '只是安卓获取imsi',
   mac                  varchar(64) default '' 				comment '只是安卓获取mac地址',
   phonetype            varchar(32) default ''           	comment '手机型号' ,
   phoneresolution      varchar(32) default ''            	comment '手机分辨率,格式为640*480',
   network              varchar(8)	default ''			    comment	'网络类型，2g,3g,4g,wifi',
   version              varchar(16)	default ''				comment	'客户端版本',
   latitude             decimal(15,8) default 0			comment '经纬度latitude',
   longitude            decimal(15,8) default 0			comment '经纬度longitude',
   systemversion        varchar(16)	 default ''				comment '系统版本',
   clientip             varchar(64)	 default ''				comment '客户端ip',
   userid               bigint(20)   not null    			COMMENT '用户id',
   mobile				varchar(20)  not null	  			COMMENT '电话号码',
   username				varchar(36)  not null default ''	COMMENT '用户名',
   invitor              varchar(20)            	 			comment '邀请人',
   openid				varchar(128)  default ''   	        comment 'openid',
   openidtype			varchar(64)	  default 'none'        comment 'openid类型,none为不使用',
   ext1                 varchar(64) DEFAULT '' 				COMMENT '保留',
   ext2                 varchar(64) DEFAULT '' 				COMMENT '保留',
   ext3                 varchar(64) DEFAULT '' 				COMMENT '保留',
   reg_time				datetime not null default '0000-00-00 00:00:00'    comment '注册时间',
   create_time          datetime not null default '0000-00-00 00:00:00'    comment '创建时间',
   primary key (id),
   key(ostype),
   key(channel),
   key(deviceid),
   key(channel),
   key(network),
   key(version),
   key(userid),
   key(mobile),
   key(username),
   key(invitor),
   key(reg_time),
   key(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='注册统计';"


