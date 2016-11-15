package com.ai.baas.mt.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SSO配置文件工具类
 *
 * Date: 2015年11月29日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author gucl
 */
public final class ConfigUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

    //config.properties属性文件
    private static Properties prop = new Properties();
    
    public static Properties getProp() {
		return prop;
	}
	public static void setProp(Properties prop) {
		ConfigUtil.prop = prop;
	}
	private ConfigUtil() {
    }

    static {
        try {

            InputStream inStream = ConfigUtil.class.getClassLoader().getResourceAsStream(
                    "config.properties");
            prop.load(inStream);
            LOG.debug("加载config.properties完毕");
        } catch (Exception e) {
            LOG.debug("加载config.properties失败，具体原因：" + e.getMessage(), e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key, "").trim();
    }

}
