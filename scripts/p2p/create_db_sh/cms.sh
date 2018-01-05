#!/bin/bash
#################################################################
#password=123456
l=0
m=0
n=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "
db=cms

$mysql_exec -e "create database ${db}"


$mysql_exec $db -e "CREATE TABLE t_cms_banner(
  id 			int(11) NOT NULL AUTO_INCREMENT   COMMENT 'id',
  ostype 		tinyint NOT NULL DEFAULT 0		  COMMENT '系统类型：0 all,1 android,2 ios, 3 pc 4 wap',
  bannertype 	tinyint NOT NULL DEFAULT 1 	      COMMENT '类型 1:首页banner',
  usertype 		tinyint NOT NULL DEFAULT 0 		  COMMENT '用户类型：0-所有 1:新手，2-老手',
  channel 		tinyint NOT NULL DEFAULT 0 		  COMMENT '渠道维度，0 所有,1选择渠道',
  version 		tinyint NOT NULL DEFAULT 0 		  COMMENT '版本号维度，0所有,1选择版本',
  name 			varchar(128) NOT NULL DEFAULT ''  COMMENT 'banner名称',
  img 			varchar(250) DEFAULT '' 		  COMMENT 'banner图片',
  back_color    varchar(8)   DEFAULT '#FFFFFF' 	 COMMENT '图片背景色',
  url 			varchar(250) DEFAULT '' 		COMMENT '链接',
  start_time 	datetime 	NOT NULL 			COMMENT '开始时间',
  end_time 		datetime 	NOT NULL 			COMMENT '结束时间',
  sort 			int(11) 	DEFAULT 0			COMMENT '排序',
  status 		tinyint     DEFAULT 0 			COMMENT '状态,0,正常，1，下架',
  creator 		varchar(32) DEFAULT '' 			COMMENT '创建人',
  update_user 	varchar(32) DEFAULT '' 		    COMMENT '修改人',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  update_time  datetime not null default '0000-00-00 00:00:00' 	comment '修改时间',
  PRIMARY KEY (id),
  KEY ostype (ostype),
  KEY bannertype (bannertype),
  KEY usertype (usertype),
  KEY channel (channel),
  KEY version (version),
  KEY name (name),
  KEY url (url),
  KEY start_time (start_time),
  KEY end_time (end_time),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner';"



$mysql_exec $db -e "CREATE TABLE t_cms_banner_channel (
  id 			bigint(20) 	NOT NULL AUTO_INCREMENT		COMMENT 'id',
  banner_id 	int(11) 	NOT NULL 					COMMENT 'banner表id',
  channel 		varchar(64) NOT NULL 					COMMENT '渠道号',
  status 		tinyint     DEFAULT 0 			    	COMMENT '状态,0,正常，1，下架',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (id),
  KEY banner_id (banner_id),
  KEY channel (channel),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner渠道表';"



$mysql_exec $db -e "CREATE TABLE t_cms_banner_version (
  id 			bigint(20) 	NOT NULL AUTO_INCREMENT		COMMENT 'id',
  banner_id 	int(11) 	NOT NULL 					COMMENT 'banner表id',
  version 	varchar(12) NOT NULL 					COMMENT '版本号',
  status 		tinyint     DEFAULT 0 			    	COMMENT '状态,0,正常，1，下架',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (id),
  KEY banner_id (banner_id),
  KEY version (version),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner版本号表';"





$mysql_exec $db -e "CREATE TABLE t_cms_splash_screen
(
 	id              int NOT NULL AUTO_INCREMENT 	  COMMENT 'id',
 	ostype 			tinyint NOT NULL DEFAULT 0		  COMMENT '系统类型：0 all,1 android,2 ios ',
 	channel 		tinyint NOT NULL DEFAULT 0 		  COMMENT '渠道维度，0 所有,1选择渠道',
 	name 			varchar(64) not null default ''				    COMMENT '名称',
 	img 			varchar(250) not null default ''				COMMENT '图片地址',
 	url 			varchar(250) not null default ''				COMMENT 'url',
 	start_time 		datetime not null default '0000-00-00 00:00:00' COMMENT '开始时间',
 	end_time   		datetime not null default '0000-00-00 00:00:00' COMMENT '结束时间',
 	status 			tinyint     DEFAULT 0 							COMMENT '状态,0,正常，1，下架',
 	sort      		INT NOT NULL DEFAULT 0 							COMMENT '排序，越大越靠前',
 	creator  		varchar(32) DEFAULT 'admin'						COMMENT '创建人',
 	create_time     datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
 	update_user     varchar(32) COMMENT '修改人',
 	update_time     datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
 	PRIMARY KEY (id),
 	key(ostype),
 	key(channel),
 	key(name),
 	key(img),
 	key(url),
  key(start_time),
 	key(end_time),
 	key(status),
 	key(sort),
 	key(create_time)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT '闪屏表';"




$mysql_exec $db -e "CREATE TABLE t_cms_splash_screen_channel
(
	id 				  int(11) NOT NULL AUTO_INCREMENT					COMMENT 'id',
	splash_screen_id  int not null 										COMMENT '闪屏id',
	channel    		  varchar(32) not null 								COMMENT '渠道号',
	status 			  tinyint     DEFAULT 0 							COMMENT '状态,0,正常，1，删除',
	create_time       datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
	PRIMARY KEY (id),
	key(splash_screen_id),
	key(channel),
	key(status),
	key(create_time)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT '闪屏渠道表';"





$mysql_exec $db -e "CREATE TABLE t_cms_upload_log (
  id			bigint(20) NOT NULL AUTO_INCREMENT 				COMMENT 'id',
  filename 		varchar(250) 									COMMENT '文件名称',
  filetype 		varchar(32) 									COMMENT '文件类型',
  filesize 		int default 0 									COMMENT '文件大小',
  creator  		varchar(32) 									COMMENT '创建人',
  upload_log 	text 											COMMENT '上传成功后文件路径',
  remark     	varchar(250) not null  							COMMENT '备注',
  create_time   datetime not null default '0000-00-00 00:00:00' comment '创建时间',
  PRIMARY KEY (id),
  KEY filename (filename),
  KEY filetype (filetype),
  KEY remark (remark),
  KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传记录表';"





$mysql_exec $db -e "CREATE TABLE t_cms_articles (
  id			bigint(20) NOT NULL AUTO_INCREMENT 				COMMENT 'id',
  title 		varchar(250) NOT NULL							COMMENT '资讯标题',
  secondtitle 	varchar(250) NOT NULL default ''				COMMENT '资讯副标题',
  type 		    tinyint NOT NULL default 1 				        COMMENT '资讯栏目，1-媒体报道，2公司资讯3-行业资讯4-公告',
  source 		varchar(128) default ''							COMMENT '资讯来源',
  sourceurl  	varchar(250) default ''							COMMENT '资讯来源URL',
  thumbnail     varchar(250) default ''						    COMMENT '缩略图', 
  summary 	    text 											COMMENT '摘要',
  keyword     	varchar(250) not null  default ''				COMMENT '关键字',
  content     	longtext not null  							    COMMENT '内容',
  readtimes     int not null default 0							COMMENT '阅读次数',
  status		tinyint not null default 0						COMMENT '状态0上架 1-下架',
  sort		    int not null default 0						    COMMENT '排序越大越靠前',
  creator     	varchar(32) not null default 'admin' 		    COMMENT '发布人',
  create_time   datetime not null default '0000-00-00 00:00:00' comment '发布时间',
  update_user   varchar(32) default '' 							COMMENT '修改人',
  update_time   datetime not null default '0000-00-00 00:00:00' comment '修改时间',
  PRIMARY KEY (id),
  KEY title (title),
  KEY type (type),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资讯中心';"








