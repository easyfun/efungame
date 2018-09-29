#!/bin/bash

BASE_DIR=/usr/local/efg_wallet/efg_wallet
CNF_DIR=/usr/local/efg_wallet/config
LIB_DIR=$BASE_DIR/lib
LOG_DIR=$BASE_DIR/logs
LIB_JARS=`ls -1 "$LIB_DIR" | grep -E "\.jar$" | awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
if [ ! -x $LOG_DIR ]
 then
 `mkdir -p $LOG_DIR`
fi

if [ $# -lt 2 ]
  then
    nohup java -Xmx1024m -Xms1024m -classpath $CNF_DIR:$LIB_JARS $1 & >> $BASE_DIR/logs/shell.log
  else
    nohup java -Xmx1024m -Xms1024m -classpath $CNF_DIR:$LIB_JARS $1 $2 >> $BASE_DIR/logs/shell.log
fi

#BASE_DIR=/usr/local/efg_wallet
##CNF_DIR=
##LIB_DIR=
#LOG_DIR=$BASE_DIR/logs
##LIB_JARS=`ls -1 "$LIB_DIR" | grep -E "\.jar$" | awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
#if [ ! -x $LOG_DIR ]
# then
# `mkdir -p $LOG_DIR`
#fi

##if [ $# -lt 2 ]
##  then
##    nohup java -Xmx1024m -Xms1024m -classpath $CNF_DIR:$LIB_JARS $1 & >> $BASE_DIR/logs/shell.log
##  else
##    nohup java -Xmx1024m -Xms1024m -classpath $CNF_DIR:$LIB_JARS $1 $2 >> $BASE_DIR/logs/shell.log
##fi

#nohup java -Xmx1024m -Xms1024m -jar efg_wallet-1.0.1-SNAPSHOT.jar & >> $BASE_DIR/logs/shell.log

