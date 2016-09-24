package com.ai.baas.mt.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.appserver.LoadConfServiceStart;

/**
 * Tomcat启动后，自动加载所有子中心的配置文件信息到zookeeper
 *
 */
public class ZkLoadCfgListener implements ServletContextListener {
	private static final long serialVersionUID = -1855987330029989070L;
	private static final Logger LOG = LoggerFactory.getLogger(ZkLoadCfgListener.class);
	private static final String CONFIG_PATH="/WEB-INF/classes/ccsprop";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZkLoadCfgListener() {
        super();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	String realPath=sce.getServletContext().getRealPath(CONFIG_PATH);
    	LOG.error("配置文件目录的真实路径="+realPath);
    	LoadConfServiceStart.main(new String[]{realPath});
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }
	
}
