#!/bin/sh
#env
APP_NAME="changhong.zkloadweb"

#set base home
RESOURCES_HOME=${CATALINA_HOME}/webapps/ROOT/WEB-INF/classes

#change config
pushd ${RESOURCES_HOME}
sed -i "s%ccs.zk_address=.*%ccs.zk_address=${ZK_ADDR}%g" ./paas/paas-conf.properties
popd


nohup ${CATALINA_HOME}/bin/catalina.sh run >> /${APP_NAME}.log