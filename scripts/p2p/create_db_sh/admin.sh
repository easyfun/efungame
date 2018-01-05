#!/bin/bash
#################################################################
#password=123456
l=0
m=0
n=0
#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "
db=admin

$mysql_exec -e "create database ${db}"


$mysql_exec $db -e " CREATE TABLE t_admin (
  adminid   		int(11) NOT NULL AUTO_INCREMENT   COMMENT 'id',
  username 		    varchar(32) NOT NULL              COMMENT '用户名',
  password 		    varchar(64) NOT NULL			  COMMENT '密码',
  realname  		varchar(64) NOT NULL			  COMMENT '姓名',
  email 	  		varchar(64) DEFAULT ''			  COMMENT '邮箱',
  phone_number   	varchar(32) DEFAULT ''			  COMMENT '手机号',
  status    		tinyint DEFAULT 0                 COMMENT '状态。0 正常 1锁定',
  creator  		    varchar(32) DEFAULT 'admin'       COMMENT '创建用户名',
  create_time  	    datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (adminid),
  UNIQUE KEY (username),
  KEY (realname),
  KEY (email),
  KEY (phone_number),
  KEY (status),
  KEY (create_time)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员表';"



$mysql_exec $db -e " CREATE TABLE t_role(
  role_id 		    int(11) NOT NULL AUTO_INCREMENT 	COMMENT '角色id',
  role_name 		varchar(100) NOT NULL 				COMMENT '角色名称',
  role_desc 		varchar(250) NOT NULL 				COMMENT '角色描述',
  role_prio		    int(11) DEFAULT NULL 				COMMENT '角色排序',
  show_sensitive    tinyint DEFAULT 0 					COMMENT '是否显示敏感信息',
  status 			tinyint DEFAULT 0                   COMMENT '状态。0 正常 1禁用',
  create_time  	    datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (role_id),
  UNIQUE KEY (role_name),
  KEY (status),
  KEY (create_time)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';"


$mysql_exec $db -e " CREATE TABLE t_admin_in_role (
  role_id 		int(11) NOT NULL 	COMMENT '角色id',
  admin_id 		int(11) NOT NULL 	COMMENT '管理员id',
  UNIQUE KEY (role_id,admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色表';"


$mysql_exec $db -e " CREATE TABLE t_res (
  res_id 			int(11) NOT NULL AUTO_INCREMENT 	COMMENT '菜单id',
  res_name 			varchar(100) NOT NULL 			    COMMENT '菜单/操作名称',
  parent_id 		int(11) 	 DEFAULT 0 			    COMMENT '父菜单id',
  res_prio 			int(11)  DEFAULT 0 					COMMENT '菜单排序,越大越靠前',
  res_type 			tinyint  NOT NULL 					COMMENT '菜单类型：1=菜单，没url的父级菜单；2=功能，有链接的菜单；3=操作，如删除、查看',
  res_front_url 	varchar(250) DEFAULT '' 			COMMENT '菜单前端html链接',
  res_url 			varchar(250) DEFAULT '' 			COMMENT '动作地址,多个地址用,分开',
  res_opt 			varchar(45) DEFAULT ''              COMMENT '功能操作 如 add,modify,delete,view,export等',
  PRIMARY KEY (res_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='菜单操作表';"


$mysql_exec $db -e " CREATE TABLE t_role_use_res (
  role_id 			int(11) NOT NULL COMMENT '角色id',
  res_id 			int(11) NOT NULL COMMENT '菜单功能id',
  UNIQUE KEY (role_id,res_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='角色菜单功能表';"



$mysql_exec $db -e " CREATE TABLE t_operation_log (
  id 				bigint(20) NOT NULL AUTO_INCREMENT 		COMMENT 'id',
  username 			varchar(32) DEFAULT NULL 				COMMENT '用户名称',
  url 				varchar(250) DEFAULT NULL 				COMMENT '请求url',
  method 			varchar(5) DEFAULT NULL 				COMMENT '请求方式，get or post',
  post_data 		varchar(2000) DEFAULT NULL 				COMMENT 'POST数据',
  get_param 		varchar(2000) DEFAULT NULL 				COMMENT '请求参数',
  remark 			varchar(100) DEFAULT NULL 				COMMENT '备注',
  create_time 		datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  PRIMARY KEY (id),
  KEY (username),
  KEY (url),
  KEY (create_time)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='后台操作日志表';"



$mysql_exec $db -e " CREATE TABLE t_login_history(
  id 			 	bigint(20) NOT NULL AUTO_INCREMENT 					COMMENT 'id',
  login_name   		varchar(128) NOT NULL								COMMENT '管理员登录名',
  login_time   		datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  logout_time  		datetime not null default '0000-00-00 00:00:00' 	comment '创建时间',
  client_ip   		varchar(64) DEFAULT NULL 							COMMENT '登录ip',
  client_type 		varchar(250) DEFAULT NULL 							COMMENT '客户端类型',
  PRIMARY KEY (id),
  KEY (login_name),
  KEY (login_time),
  KEY (client_ip)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='管理员登陆历史表';"



$mysql_exec $db -e "INSERT INTO t_admin VALUES ('1', 'admin', '4478294166928c73c71ba7dc875f2225560bc753', '管理员', '', '', '0', 'admin', now());"

$mysql_exec $db -e "INSERT INTO t_res VALUES ('1', '会员管理', '0', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('2', '会员基础信息', '1', '0', '2', 'member/member.html', '', '');
INSERT INTO t_res VALUES ('3', '资产管理', '0', '99', '1', '', '', '');
INSERT INTO t_res VALUES ('4', '产品管理', '0', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('5', '运营管理', '0', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('6', '内容管理', '0', '80', '1', '', '', '');
INSERT INTO t_res VALUES ('7', '资金管理', '0', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('8', '推广管理', '0', '88', '1', '', '', '');
INSERT INTO t_res VALUES ('15', '合作机构管理', '3', '0', '2', 'coopOrgan/coopOrgan.html', '', '');
INSERT INTO t_res VALUES ('16', '系统管理', '0', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('17', '后台用户管理', '16', '100', '2', 'authManage/admin.html', 'AdminWeb/admin/getAdminList.json', '');
INSERT INTO t_res VALUES ('18', '角色管理', '16', '99', '2', 'authManage/role.html', 'AdminWeb/role/getRoleList.json', '');
INSERT INTO t_res VALUES ('19', '菜单管理', '16', '98', '2', 'authManage/menu.html', '', '');
INSERT INTO t_res VALUES ('20', '添加', '17', '100', '3', 'authManage/adminAdd.html', '', '_add');
INSERT INTO t_res VALUES ('21', '编辑', '17', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('22', '添加', '18', '0', '3', '', '', '_add');
INSERT INTO t_res VALUES ('23', '编辑', '18', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('24', '编辑', '19', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('25', '添加', '15', '0', '3', 'coopOrgan/coopOrganAdd.html', '', '_add');
INSERT INTO t_res VALUES ('26', '编辑', '15', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('27', '业务类型管理', '3', '0', '2', 'serviceType/serviceType.html', '', '');
INSERT INTO t_res VALUES ('28', '添加', '27', '0', '3', 'serviceType/serviceTypeAdd.html', '', '_add');
INSERT INTO t_res VALUES ('29', '编辑', '27', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('30', '借款录入', '3', '0', '2', 'loadEntry/loanEntryAdd.html', '', '');
INSERT INTO t_res VALUES ('31', '借款初审', '3', '0', '2', 'loanReview/loanReviewStart.html', '', '');
INSERT INTO t_res VALUES ('32', '借款终审', '3', '0', '2', 'loanReview/loanReviewSecond.html', '', '');
INSERT INTO t_res VALUES ('33', '审核', '31', '0', '3', '', '', '_audit');
INSERT INTO t_res VALUES ('34', '详情', '31', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('35', '审核', '32', '0', '3', '', '', '_audit');
INSERT INTO t_res VALUES ('36', '详情', '32', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('37', '供应商管理', '8', '0', '2', 'supplier/supplier.html', '', '');
INSERT INTO t_res VALUES ('38', '渠道管理', '8', '0', '2', 'channel/channel.html', '', '');
INSERT INTO t_res VALUES ('39', '理财顾问管理', '8', '0', '2', 'moneyAdviser/moneyAdviser.html', '', '');
INSERT INTO t_res VALUES ('40', '添加', '37', '0', '3', 'supplier/supplierAdd.html', '', '_add');
INSERT INTO t_res VALUES ('41', '编辑', '37', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('42', '添加', '38', '0', '3', 'channel/channelAdd.html', '', '');
INSERT INTO t_res VALUES ('43', '编辑', '38', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('44', '添加', '39', '0', '3', 'moneyAdviser/moneyAdviserAdd.html', '', '_add');
INSERT INTO t_res VALUES ('45', '编辑', '39', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('46', '资讯管理', '6', '0', '2', 'contentManage/contentManage.html', '', '');
INSERT INTO t_res VALUES ('47', '闪屏管理', '6', '0', '2', 'splashScreen/splashScreen.html', '', '');
INSERT INTO t_res VALUES ('48', '添加', '46', '0', '3', 'contentManage/contentManageAdd.html', '', '_add');
INSERT INTO t_res VALUES ('49', '编辑', '46', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('50', '添加', '47', '0', '3', 'splashScreen/splashScreenAdd.html', '', '_add');
INSERT INTO t_res VALUES ('51', '编辑', '47', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('52', '散标管理', '4', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('53', '未发布散标列表', '52', '0', '2', 'scatteredLoan/scatteredLoan.html', 'AssetAdminWeb/asset/listAsset2Release.json', '');
INSERT INTO t_res VALUES ('54', '体验标管理', '4', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('55', '体验标招标管理', '54', '0', '2', 'expScale/expScale.html', '', '');
INSERT INTO t_res VALUES ('57', '添加', '55', '0', '3', 'expScale/expScaleAdd.html', '', '_add');
INSERT INTO t_res VALUES ('58', '编辑', '55', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('60', '费用配置', '16', '0', '2', 'costSet/costSet.html', '', '');
INSERT INTO t_res VALUES ('61', '编辑', '60', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('63', '卡券管理', '5', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('64', '优惠券配置', '63', '0', '2', 'coupon/couponSetList.html', '', '');
INSERT INTO t_res VALUES ('65', '新增', '64', '0', '3', '', '', '_add');
INSERT INTO t_res VALUES ('66', '手机端登录记录', '1', '0', '2', 'logRecord/mobLogRecord.html', '', '');
INSERT INTO t_res VALUES ('67', '编辑拉黑冻结', '2', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('68', '详情', '2', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('69', '发布散标', '53', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('70', '详情', '46', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('71', '详情', '55', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('72', '查看', '39', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('73', '退回', '53', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('74', '详情', '53', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('75', '详情', '27', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('76', '删除', '37', '0', '3', '', '', '_delete');
INSERT INTO t_res VALUES ('77', '上传文件列表', '16', '0', '3', '', 'CMSAdminWeb/upload/uploadFile.json', '_modify');
INSERT INTO t_res VALUES ('78', '未分配债权池', '3', '0', '2', 'loanReview/undistribuLoan.html', 'AssetAdminWeb/asset/listNondistributionAsset.json,AssetAdminWeb/asset/distributionAsset.json', '');
INSERT INTO t_res VALUES ('79', 'banner管理', '6', '0', '2', 'banner/banner.html', 'CMSAdminWeb/Banner/getBannerList.json,CMSAdminWeb/Banner/addBanner.json,CMSAdminWeb/Banner/getBannerById.json,CMSAdminWeb/Banner/updateBanner.json,CMSAdminWeb/Banner/updateBannerStatus.json,CMSAdminWeb/upload/uploadFile.json', '');
INSERT INTO t_res VALUES ('80', '添加', '79', '0', '3', '', '', '_add');
INSERT INTO t_res VALUES ('81', '编辑', '79', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('82', '上传文件列表', '6', '0', '2', 'uploadFile/upload.html', 'CMSAdminWeb/upload/uploadLogList.json,CMSAdminWeb/upload/uploadFile.json', '');
INSERT INTO t_res VALUES ('83', '添加', '82', '0', '3', '', '', '_add');
INSERT INTO t_res VALUES ('84', '编辑', '64', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('85', '导出', '64', '0', '3', '', '', '_export');
INSERT INTO t_res VALUES ('86', '优惠券派发管理', '63', '0', '2', 'coupon/couponOutList.html', 'RewardAdminWeb/couponDispatch/addCouponDispatch.json,RewardAdminWeb/couponDispatch/getCouponDispatchList.json,RewardAdminWeb/couponDispatch/getCouponDispatchDetailList.json', '');
INSERT INTO t_res VALUES ('87', '添加', '86', '0', '3', '', '', '_add');
INSERT INTO t_res VALUES ('88', '详情', '86', '0', '3', '', '', '_view');
INSERT INTO t_res VALUES ('89', '债权转让管理', '4', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('90', '债权转让配置', '89', '0', '2', 'bondTrans/bondTransSet.html', 'debtconfig/queryDebtConfig.json,debtconfig/saveDebtConfig.json', '');
INSERT INTO t_res VALUES ('91', '理财计划管理', '4', '0', '1', '', '', '');
INSERT INTO t_res VALUES ('92', '通用设置', '91', '0', '2', 'moneyManage/commonSet.html', '/AdminZookeeperWeb/commonconfig/queryCommonConfig.json,AdminZookeeperWeb/commonconfig/saveCommonConfig.json', '');
INSERT INTO t_res VALUES ('93', '编辑', '92', '0', '3', '', '', '_modify');
INSERT INTO t_res VALUES ('94', '理财计划配置', '91', '99', '2', 'moneyManage/moneyManageSet.html', 'AssetAdminWeb/asset/listPlanBase.json,AssetAdminWeb/asset/addPlanBase.json', '');
INSERT INTO t_res VALUES ('95', '新增', '94', '0', '3', '', '', '_add');
INSERT INTO t_res VALUES ('96', '发布理财计划', '91', '0', '2', 'moneyManage/moneyManageAdd.html', 'AssetAdminWeb/asset/releaseAsset2Loan.json,AssetAdminWeb/asset/listPlanBase.json', '');
INSERT INTO t_res VALUES ('97', '已发布散标列表', '52', '0', '2', 'scatteredLoan/scatteredLoanIssued.html', 'AssetAdminWeb/asset/listAsset2Release.json', '');
INSERT INTO t_res VALUES ('98', '已发布理财计划列表', '91', '0', '2', 'moneyManage/moneyManageIssued.html', 'AssetAdminWeb/asset/listAsset2Release.json', '');"



