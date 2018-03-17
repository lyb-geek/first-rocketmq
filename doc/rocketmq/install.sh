#!/bin/sh
#创建组和用户
echo "创建组(mquser)和用户(mquser)开始"
chattr -i /etc/group;chattr -i /etc/gshadow;chattr -i /etc/passwd;chattr -i /etc/shadow
groupadd mquser
useradd -g mquser mquser
chattr +i /etc/group;chattr +i /etc/gshadow;chattr +i /etc/passwd;chattr +i /etc/shadow
echo "创建组和用户结束"

#配置用户的最大句柄数
echo "给用户配置最大句柄数开始"
isSetLimit=`grep mquser /etc/security/limits.conf | wc -l`
if [[ $isSetLimit = 0 ]]; then
    echo "mquser soft nofile 65536" >> /etc/security/limits.conf
    echo "mquser hard nofile 131072" >> /etc/security/limits.conf
fi
echo "给用户(mquser)配置最大句柄数结束"


#安装rocketmq
echo "安装rocketmq开始"
echo "1、解压已经编译好的文件"
tar zxf /oa/other/rocketmq/apache-rocketmq.tar.gz
echo "2、设置rocketmq环境变量"
echo 'export rocketmq=/oa/other/rocketmq' >> /etc/profile
echo 'export PATH=$PATH:$rocketmq/bin' >>/etc/profile
source /etc/profile
echo $rocketmq
echo "3、给命令mqadmin mqbroker mqfiltersrv mqshutdown  mqnamesrv赋予可执行权限"
cd /oa/other/rocketmq/bin
chmod +x mqadmin mqbroker mqfiltersrv mqshutdown  mqnamesrv
echo "4、修改rocketmq启动服务脚本对于内存的限制"
sed -i 's/${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m/${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m/g' runserver.sh 
sed -i 's/${JAVA_OPT} -server -Xms8g -Xmx8g -Xmn4g/${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn512m/g' runbroker.sh
echo "5、修改日志配置文件"
cd /oa/other/rocketmq
if [ ! -d "/logs/" ]; then  
mkdir logs
else
echo "目录/oa/other/rocketmq/logs已经存在"
fi 
cd /oa/other/rocketmq/conf && sed -i 's#${user.home}#/oa/other/rocketmq#g' *.xml
echo "6、创建存储路径"
cd /oa/other/rocketmq
if [ ! -d "/store/" ]; then  
mkdir store
mkdir store/commitlog
mkdir store/consumequeue
mkdir store/index
else
echo "目录/oa/other/rocketmq/store已经存在"
fi 
echo "7、修改配置文件"
echo 'namesrvAddr=localhost:9876' >> /oa/other/rocketmq/conf/broker.conf
echo 'autoCreateTopicEnable=true' >> /oa/other/rocketmq/conf/broker.conf
echo 'storePathRootDir=/oa/other/rocketmq/store' >> /oa/other/rocketmq/conf/broker.conf
echo 'storePathCommitLog=/oa/other/rocketmq/store/commitlog' >> /oa/other/rocketmq/conf/broker.conf
echo 'storePathConsumeQueue=/oa/other/rocketmq/store/consumequeue' >> /oa/other/rocketmq/conf/broker.conf
echo 'storePathIndex=/oa/other/rocketmq/store/index' >> /oa/other/rocketmq/conf/broker.conf
echo "8、配置rocketmq防火墙"
isFirewallRunning=`firewall-cmd --state | wc -l`
if [[ $isFirewallRunning = 0 ]]; then  
echo "当前系统防火墙处于关闭状态，待防火墙开启再手动配置"
else
firewall-cmd --zone=public --add-port=9876/tcp --permanent
firewall-cmd --zone=public --add-port=10911/tcp --permanent
firewall-cmd --reload
fi 
chown -R mquser:mquser /oa/other/rocketmq
echo "安装rocketmq结束。如需操作rocketmq，请切换至用户mquser进行相关操作"

