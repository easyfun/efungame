#!/bin/bash
#################################################################
#################################################################
password=123456

m=0
n=0

#mysql_exec="mysql -uroot -p$password"
mysql_exec="mysql "


#zyxr ������
db=account
$mysql_exec -e "drop database ${db}"
$mysql_exec -e "create database ${db}"
$mysql_exec $db -e "create table t_id_config
(
  id int(11) NOT NULL COMMENT '�û�ID����',
  id_index text NOT NULL COMMENT '�û�����ID',
  nr_peralloc int(11) NOT NULL COMMENT 'Ԥ��id��',
  last_update_time datetime NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

$mysql_exec $db -e "create table t_increase_id_config
(
  id int(11) NOT NULL COMMENT '�û�ID����',
  id_index mediumtext NOT NULL COMMENT '�û�����ID',
  nr_peralloc int(11) NOT NULL COMMENT 'Ԥ��id��',
  last_update_time datetime NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

#����id server
for i in {0..25}
do
$mysql_exec $db -e "insert into  t_id_config 
(id,id_index,nr_peralloc,last_update_time)	   
 values 
($i,0,100,now());"
done

update t_id_config set nr_peralloc=5, id_index=1  where id=24;

#����id server
for i in {0..5}
do
$mysql_exec $db -e "insert into  t_increase_id_config 
(id,id_index,nr_peralloc,last_update_time)	   
 values 
($i,0,100,now());"
done


for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	
	# �û��˻���
	$mysql_exec $db -e "create table t_user_account_${m}${n}
	(
	   acc_id             bigint not null default 0 comment '�˻�ID',
	   uid                bigint not null default 0 comment '�û�UID',
	   user_name          varchar(64) not null default '' comment '�û���',
	   type               int not null default 0 comment '�˻����� 1. �ױ�֧�� 2.����֧�� 3. ����֧��ƽ̨',
	   balance            bigint not null default 0 comment '���',
	   frozen             bigint not null default 0 comment '������',
	   state              tinyint not null default 0 comment '״̬��1-������2-���ᣬ3-�����쳣',
	   parent             bigint not null default 0 comment '���˻�ID',
	   deposit_amt        bigint not null default 0 comment '�����ֵ���',
	   update_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '�����ֵʱ��',
	   primary key (acc_id),
	   unique key i_type_uid (type, uid)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

	# �û��˻���ˮ��
	$mysql_exec $db -e "create table t_user_account_flow_${m}${n}
	(
	   flow_id            bigint not null default 0 comment '��ˮID',
	   acc_id             bigint not null default 0 comment '�˻�ID',
	   uid                bigint not null default 0 comment '�û�ID',
	   #20160829Ϊ���ݵȵ�ʱ�����ˮ  
	   type               int not null default 0 comment '�˻����� 1. �ױ�֧�� 2.����֧�� 3. ����֧��ƽ̨',
	   bus_type           tinyint not null default 0 comment 'ҵ�����ͣ�1-��ֵ��2-���֣�3-Ͷ�ʣ�4-�ɷѣ�5-�ſ6-����',
	   subbus_type        tinyint not null default 0 comment '��ҵ�����ͣ�1-gpsѺ��2-��ѯ�����, ........',
	   flow_type          tinyint not null default 0 comment '��ˮ���ͣ�1-ɢ����ˮ��2-��Ƽƻ���ˮ',
	   operation          int not null default 0 comment '������1-������2-������3-���ᣬ4-�ⶳ',
	   amount             bigint not null default 0 comment '�䶯���',
	   balance            bigint not null default 0 comment '���������',
	   frozen             bigint not null default 0 comment '�ı�֮��Ķ�����',
	   peer_acc_id        bigint not null default 0 comment '���׶Է��˻�ID',
	   peer_uid           bigint not null default 0 comment '�Է����û�id',
	   trans_id           bigint not null default 0 comment '���¸���ˮ��ҵ����ID',
	   loan_id            bigint not null default 0 comment '���ID',
	   repayment_id       bigint not null default 0 comment '����ƻ�ID',
	   remark             varchar(500) not null default '' comment '����',
	   create_index       bigint not null auto_increment comment '������¼�������ֶ�',
	   freeze_id          bigint not null default 0 comment '���ᵥID',
	   service_name       varchar(16) not null default '' comment '�����߷�������',
	   deposit_amt        bigint not null default 0 comment '�����ֵ���',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '�����ֵʱ��',
	   primary key (flow_id),
	   unique key i_btype_operation_tranid (bus_type, operation, trans_id),
	   key i_create_index (create_index)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
done




# �ʽ��˻���
$mysql_exec $db -e "create table t_biz_account
(
   acc_id             bigint not null default 0 comment '�˻�ID',
   type               int not null default 0 comment '�˻����� 1. �ױ�֧�� 2.����֧�� 3. ����֧��ƽ̨',
   balance            bigint not null default 0 comment '���',
   frozen             bigint not null default 0 comment '������',
   state              tinyint not null default 0 comment '״̬��1-������2-���ᣬ3-�����쳣',
   parent             bigint not null default 0 comment '���˻�',
   deposit_amt        bigint not null default 0 comment '�����ֵ���',
   update_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
   create_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '�����ֵʱ��',
   primary key (acc_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


for i in {0..99}
do	
	m=$(($i/10))
	n=$(($i%10))
	# �ʽ��˻���ˮ��
	$mysql_exec $db -e "create table t_biz_account_flow_${m}${n}
	(
	   flow_id            bigint not null default 0 comment '��ˮID',
	   bus_type           tinyint not null default 0 comment 'ҵ�����ͣ�����',
	   operation          int not null default 0 comment '������1-������2-������3-ת�����ᣬ4-ת�붳��',
	   acc_id             bigint not null default 0 comment '�˻�ID',
	   type               int not null default 0 comment '�˻����� 1. �ױ�֧�� 2.����֧�� 3. ����֧��ƽ̨',
	   amount             bigint not null default 0 comment '�䶯���',
	   peer_uid           bigint not null default 0 comment '�Է��û�id',
	   remark             varchar(500) not null default '' comment '����',
	   peer_acc_id        bigint not null default 0 comment '���׶Է��˻�ID',
	   trans_id           bigint not null default 0 comment '���¸���ˮ��ҵ����ID',
	   loan_id            bigint not null default 0 comment '���ID',
	   repayment_id       bigint not null default 0 comment '����ƻ�ID',
	   balance            bigint not null default 0 comment '���������',
	   frozen             bigint not null default 0 comment '�ı�֮��Ķ�����',
	   create_index       bigint not null auto_increment comment '���������ֶ�',
	   freeze_id          bigint not null default 0 comment '���ᵥID',
	   service_name       varchar(16) not null default '' comment '�����߷�������',
	   deposit_amt        bigint not null default 0 comment '�����ֵ���',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	   deposit_time        datetime not null default '0000-00-00 00:00:00' comment '�����ֵʱ��',
	   primary key (flow_id),
	   unique key i_btype_operation_transid (bus_type, operation, trans_id),
	   key i_create_index (create_index)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"

        # ���ᵥ��
	$mysql_exec $db -e "create table t_freeze_order_${m}${n}
	(
	   order_id           bigint not null default 0 comment '���ᵥ�ţ������ߴ���',
	   acc_id             bigint not null default 0 comment '�����˺�id',
	   uid           bigint not null default 0 comment '�����û�id',
	   amount             bigint not null default 0 comment '�����ܽ��',
	   bus_type           int not null default 0 comment 'ҵ������',
	   unfrozen_amount    bigint not null default 0 comment '�ѽⶳ���',
	   loan_id            bigint not null default 0 comment '���ID',
	   state              int not null default 0 comment '״̬��1-���ᣬ2-���ֽⶳ��3-��ȫ���ⶳ',
	   remark             varchar(64) not null default '' comment '����',
	   update_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	   create_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	   primary key (order_id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	
done	

# ���ֺ�������
$mysql_exec $db -e "create table t_user_account_withdrawblacklist
(
	acc_id             bigint not null default 0 comment '�˻�ID',
	uid                bigint not null default 0 comment '�û�UID',
	user_name          varchar(64) not null default '' comment '�û���',
	type               int not null default 0 comment '�˻����� 1. �ױ�֧�� 2.����֧�� 3. ����֧��ƽ̨',
	balance            bigint not null default 0 comment '���',
	frozen             bigint not null default 0 comment '������',
	state              tinyint not null default 0 comment '״̬��1-������2-���ᣬ3-�����쳣',
	parent             bigint not null default 0 comment '���˻�ID',
	deposit_amt        bigint not null default 0 comment '�����ֵ���',
	withdraw_status	  tinyint not null default 0 comment '״̬��1-������0-���ᣬ2-û�п���',
	update_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	create_time        datetime not null default '0000-00-00 00:00:00' comment '����ʱ��',
	deposit_time        datetime not null default '0000-00-00 00:00:00' comment '�����ֵʱ��',
	primary key (acc_id),
	unique key i_type_uid (type, uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;"


