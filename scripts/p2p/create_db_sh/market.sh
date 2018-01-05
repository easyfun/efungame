#!/bin/bash
#################################################################
#password=123456
l=0
m=0
n=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "
db=market

$mysql_exec -e "create database ${db}"


$mysql_exec $db -e "CREATE TABLE t_provider (
  name	   varchar(64)  NOT NULL 			COMMENT '供应商名称',
  contact    varchar(64)  DEFAULT ''    COMMENT '供应商联系人',
  mobile     varchar(64)  DEFAULT ''    COMMENT '供应商联系人电话',
  remark     varchar(512) DEFAULT ''			COMMENT '备注', 
  status       tinyint      DEFAULT 0		COMMENT '状态0正常',
  creator      varchar(64)  DEFAULT 'admin' 			COMMENT '创建人',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  update_user  varchar(64)  DEFAULT '' 				COMMENT '修改人',
  update_time  datetime not null default '0000-00-00 00:00:00' 	comment '修改时间',
  PRIMARY KEY (name),
  KEY contact (contact),
  KEY mobile (mobile),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='供应商表';"



$mysql_exec $db -e "CREATE TABLE t_channel (
  channel_index		varchar(8)  NOT NULL 				COMMENT '渠道索引(8位随机字符)',
  channel_code		varchar(32)  NOT NULL 				COMMENT '渠道编号',
  channel_type		tinyint      NOT NULL DEFAULT 0 	COMMENT '渠道类型 0-商务1-门店',
  channel_name  	varchar(64)  NOT NULL 				COMMENT '渠道名称',
  provider       varchar(64)  DEFAULT ''     COMMENT '供应商名称',
  admin_group     varchar(64)  DEFAULT ''     COMMENT '分组，数字营销组',
  admin_user   varchar(64)  DEFAULT ''     COMMENT '渠道负责人',
  status       tinyint      DEFAULT 0					COMMENT '状态0正常',
  creator      varchar(64)  DEFAULT 'admin' 			COMMENT '创建人',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  update_user  varchar(64)  DEFAULT '' 				COMMENT '修改人',
  update_time  datetime not null default '0000-00-00 00:00:00' 	comment '修改时间',
  PRIMARY KEY (channel_index),
  UNIQUE KEY channel_code (channel_code),
  KEY channel_type (channel_type),
  KEY channel_name (channel_name),
  KEY provider (provider),
  KEY admin_group (admin_group),
  KEY admin_user (admin_user),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='渠道表';"




$mysql_exec $db -e "CREATE TABLE t_financial_advisor (
  id           bigint(20)   NOT NULL AUTO_INCREMENT 	COMMENT 'id',
  userid       bigint(20)   NOT NULL   	              COMMENT '理财师用户ID',
  realname     varchar(64)  default ''   	            COMMENT '理财师用户姓名',
  mobile       varchar(20)  NOT NULL                  COMMENT '理财师用户手机号',
  channel_code varchar(32)  NOT NULL 				    COMMENT '渠道编号',
  usercount    int      DEFAULT 0					    COMMENT '客户数量',
  status       tinyint      DEFAULT 0					COMMENT '状态0开启',
  creator      varchar(64)  DEFAULT 'admin' 			COMMENT '创建人',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  update_user  varchar(64)  DEFAULT '' 				COMMENT '修改人',
  update_time  datetime not null default '0000-00-00 00:00:00' 	comment '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY userid (userid),
  KEY realname (realname),
  KEY mobile (mobile),
  KEY channel_code (channel_code),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='理财顾问';"



$mysql_exec $db -e "CREATE TABLE t_financial_user (
  id           bigint(20)   NOT NULL AUTO_INCREMENT 	COMMENT 'id',
  advisor_id   bigint(20)   NOT NULL  				COMMENT '关联理财顾问表id',
  userid       bigint(20)   NOT NULL   	      COMMENT '客户id',
  mobile       varchar(20)  NOT NULL   	      COMMENT '客户手机号',
  realname     varchar(64)  default ''   	    COMMENT '客户姓名',
  status       tinyint      DEFAULT 0					COMMENT '状态0正常，1未分配',
  creator      varchar(64)  DEFAULT 'admin' 			COMMENT '创建人',
  create_time  datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  update_user  varchar(64)  DEFAULT '' 				COMMENT '修改人',
  update_time  datetime not null default '0000-00-00 00:00:00' 	comment '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY userid (userid),
  KEY advisor_id (advisor_id),
  KEY mobile (mobile),
  KEY realname (realname),
  KEY status (status),
  KEY create_time (create_time)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8  COMMENT='理财顾问客户列表';"

$mysql_exec -e "insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('WG2CYxpT','M_FSC_AnNingKunGang',1,'安宁昆钢金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('VfFhtSTn','M_WC_CuiHu',1,'翠湖财富中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('iCTQTKBA','M_WC_DongJiaWang',1,'董家湾财富中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('Yzt0IRV6','M_FSC_ChangHong',1,'昌宏金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('lMvEFFTS','M_FSC_ChuanJinLu',1,'穿金路金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('U05QwEsA','M_CiBaDian',1,'茨坝店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('M6lO3bBr','M_SC_DaLiHeQingYunHe',1,'大理鹤庆云鹤服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('EHSQTvEN','M_FSC_DiTaiSi',1,'地台寺金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('995vQVIF','M_FSC_DongHua',1,'东华金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('LK6YeMJU','M_FSC_DongSiJie',1,'东寺街金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('L5viZPiG','M_WC_GuoMao',1,'国贸财富中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('zcWcnV5I','M_FSC_HuaDou',1,'华都金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('3vMh6e1f','M_HuanChengDongLuDian',1,'环城东路店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('5Wz1zfkW','M_JM_HeQingDian',1,'加盟-鹤庆店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('BDazAhwm','M_JM_JiangAnDian',1,'加盟-江岸店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('lZxzkvZc','M_JM_XiangGeLiLa',1,'加盟-香格里拉店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('w78GUmsT','M_FSC_JiangAn',1,'江岸金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('5i71PDeX','M_FSC_JiaoLingLu',1,'茭菱路金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('MHcjIAAt','M_WC_JinHuaPuLu',1,'近华浦路财富中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('J3cku7tq','M_FSC_LiYangXingCheng',1,'丽阳星城金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('aSCi6hwm','M_FSC_LiangYuan',1,'梁源金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('USzomt4U','M_FSC_LuoPing',1,'罗平金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('jnlmglSk','M_ZYD_LuoPingDian',1,'罗平直营店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('4xBDV3Ex','M_FSC_MeiGuiWang',1,'玫瑰湾金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('SPtfk2xj','M_FSC_NanYa',1,'南亚金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('rauQ5xDk','M_FSC_QianWeiXiLu',1,'前卫西路金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('S8GkhLq9','M_FSC_QuJing',1,'曲靖金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('jWrtu0yV','M_FSC_RiXinDongLuDian',1,'日新东路店金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('IfJlzTq3','M_FSC_ShiJiCheng',1,'世纪城金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('zMKYQ5cF','M_FGS_ChongMing',1,'嵩明分公司',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('Nw2tlybT','M_FSC_TangZiXiang',1,'塘子巷金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('J9es27HO','M_FSC_WuJingLu',1,'吴井路金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('YONSpxwy','M_FSC_XiYuanLu',1,'西园路金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('ZTI6LflB','M_FSC_XiangGeLiLa',1,'香格里拉金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('XEegvMe7','M_ZYD_XinWenLu',1,'新闻路直营店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('OPejdAJh','M_FSC_XingYuan',1,'兴苑金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('K28UKLOC','M_FSC_YuXiHongTa',1,'玉溪红塔金融服务中心',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('6mLy9gyM','M_ZYD_DongHuaDian',1,'直营-东华店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('G56S9xba','M_ZYD_HongYunDian',1,'直营-红云店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('MQPRrdrF','M_ZYD_JiaYuanDian',1,'直营-佳园店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('NpCYOTik','M_ZYD_XiYuanLuDian',1,'直营-西园路店',0,  now()) ;
insert into market.t_channel (channel_index,channel_code,channel_type,channel_name,status,create_time) values ('HekGdBAc','M_TYD_GaoXingDian',1,'中业兴融互联网金融体验店--高新店',0,  now()) ;"
