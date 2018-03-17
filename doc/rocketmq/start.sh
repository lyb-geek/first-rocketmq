#!/bin/sh
nohup sh /oa/other/rocketmq/bin/mqnamesrv >/dev/null 2>&1 &
nohup sh /oa/other/rocketmq/bin/mqbroker -c /oa/other/rocketmq/conf/broker.conf >/dev/null 2>&1 &
jps