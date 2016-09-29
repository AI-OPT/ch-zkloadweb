#!/bin/sh

#set env start.....
WEBAPP_PATH=${CATALINA_HOME}/webapps/ROOT
WEBAPP_CLASSES_PATH=${CATALINA_HOME}/webapps/ROOT/WEB-INF/classes
export WEBAPP_PATH
export WEBAPP_CLASSES_PATH

for file in ${WEBAPP_PATH}/WEB-INF/lib/*.jar;
do CP=${CP}:$file;
done

CLASSPATH="${CP}"
export CLASSPATH

CLASSPATH="${CLASSPATH}:${WEBAPP_CLASSES_PATH}"

JAVA_OPTIONS=" -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -Dsun.net.inetaddr.ttl=10"
MEM_ARGS="-Xms128m -Xmx512m"
#set env end.....

PROCESS_NAME="LoadConfServiceStart"
PROCESS_PARM="opt.zkloader.system=changhong"
CUR_USER=`whoami`
RUNNER_PRODUCT_NAME=opt.zkloader
LOG_PATH=/opt-zkloader.log
CLASSPATH="${CLASSPATH}"
MEM_ARGS="-Xms256m -Xmx512m -XX:PermSize=64M -XX:MaxPermSize=128M"

. "${WEBAPP_CLASSES_PATH}/setEnv.sh"

cd ${WEBAPP_CLASSES_PATH}
        
${JAVA_HOME}/bin/java ${MEM_ARGS} -D${PROCESS_PARM}  ${JAVA_OPTIONS} com.ai.opt.sdk.appserver.LoadConfServiceStart ccsprop  >> $LOG_PATH 


tail -100f $LOG_PATH




