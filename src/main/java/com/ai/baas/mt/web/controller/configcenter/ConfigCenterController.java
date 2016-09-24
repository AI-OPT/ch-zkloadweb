package com.ai.baas.mt.web.controller.configcenter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.baas.mt.web.model.ConfigInfo;
import com.ai.baas.mt.web.util.StringHelper;
import com.ai.net.xss.util.StringUtil;
import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;

@Controller
@RequestMapping("/configCenter")
public class ConfigCenterController {
    private static final Logger LOG = Logger.getLogger(ConfigCenterController.class);

    @RequestMapping("/toQueryConfig")
    public ModelAndView toQueryConfig(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("jsp/configcenter/queryConfig");
        return view;
    }

    @RequestMapping("/toEditConfig")
    public ModelAndView toUpdateConfig(HttpServletRequest request, String name) {
        request.setAttribute("appName", name);
        ModelAndView view = new ModelAndView("jsp/configcenter/editConfig");
        return view;
    }

    /**
     * 配置加载查询
     * 
     * @param request
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ResponseData<List<ConfigInfo>> getList(HttpServletRequest request, String appName,
            String path) {
        ResponseData<List<ConfigInfo>> responseData = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        try {
        	IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
        	List<ConfigInfo> configList = new ArrayList<ConfigInfo>();
        	if(configClient.exists(path)){
        		List<String> list = configClient.listSubPath(path);
        		if (!CollectionUtil.isEmpty(list)) {
        			for (String pathValue : list) {
        				ConfigInfo config = new ConfigInfo();
        				config.setPath(pathValue);
        				if (configClient.exists(path + "/" + pathValue)) {
        					String value = configClient.get(path + "/" + pathValue);
        					config.setValue(value);
        				} else {
        					config.setValue("");
        				}
        				configList.add(config);
        			}
        		}
        	}
        	
            responseData = new ResponseData<List<ConfigInfo>>(ResponseData.AJAX_STATUS_SUCCESS,
                    "查询成功", configList);
        } catch (Exception e) {
            responseData = new ResponseData<List<ConfigInfo>>(ResponseData.AJAX_STATUS_FAILURE,
                    "获取信息异常");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }

    @RequestMapping("/deleteConfig")
    @ResponseBody
    public ResponseData<String> deleteConfig(HttpServletRequest request,
            @RequestParam("pathes[]") String[] pathes, String path, String appName) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        ResponseData<String> responseData = null;
        try {
        	IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            for (String pathId : pathes) {
                configClient.remove(path + "/" + pathId);
            }
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "删除成功",
                    "000000");
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "删除失败");
            LOG.error("删除配置时出错：", e);
        }
        return responseData;
    }

    @RequestMapping("/addConfig")
    @ResponseBody
    public ResponseData<String> addConfig(HttpServletRequest request, String data, String path,
            String appName) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        ResponseData<String> responseData = null;
        try {
        	IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            if (configClient.exists(path)) {
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "节点已存在",
                        "9999");
                return responseData;
            }
            configClient.add(path, data);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功",
                    "000000");
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "添加失败");
            LOG.error("添加配置时出错：", e);
        }
        return responseData;
    }

    @RequestMapping("/queryEditConfig")
    @ResponseBody
    public ResponseData<ConfigInfo> queryEditConfig(HttpServletRequest request, String allPath,
            String path, String appName) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        ResponseData<ConfigInfo> responseData = null;
        try {
        	IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            ConfigInfo config = new ConfigInfo();
            if (configClient.exists(allPath + "/" + path)) {
                String value = configClient.get(allPath + "/" + path);
                config.setValue(value);
                config.setPath(path);
            }
            responseData = new ResponseData<ConfigInfo>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
                    config);
        } catch (Exception e) {
            responseData = new ResponseData<ConfigInfo>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("查询配置时出错：", e);
        }
        return responseData;
    }

    @RequestMapping("/editConfig")
    @ResponseBody
    public ResponseData<String> editConfig(HttpServletRequest request, String data, String path,
            String appName) {
        ResponseData<String> responseData = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        try {
            
        	IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            configClient.modify(path, data);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "修改成功",
                    "000000");
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改失败");
            LOG.error("修改配置时出错：", e);
        }
        return responseData;
    }

    // 导入文件
    @RequestMapping("/importConfig")
    @ResponseBody
    public ResponseData<String> importConfig(HttpServletRequest request, 
            String filePath, String appName) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        ResponseData<String> responseData = null;
        Properties props = new Properties();
        //String enter = "\r\n";
        try {
          
        	IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            MultipartRequest multipartRequest = (MultipartRequest) request;
            MultipartFile uploadFile = multipartRequest.getFile("uploadFile");
            InputStream in = uploadFile.getInputStream();
            props.load(in);
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String Property = props.getProperty(key);
                //判断是否符合json格式
                 //boolean isJsonFlag = new CheckIsJson().validate(Property);//
                 boolean isJsonFlag = true;//暂时去掉校验
                if(!isJsonFlag){
                   return responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "导入数据格式错误",
                            "000001");
                }
                if (configClient.exists(key)) {
                    configClient.modify(key, Property);
                } else {
                    configClient.add(key, Property);
                }
            }
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "导入成功",
                    "000000");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("导入文件时出错：", e);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "导入失败",
                    "9999");
        }
        return responseData;
    }

    // 导出文件
    @RequestMapping("/downConfig")
    public void downConfig(HttpServletRequest request, HttpServletResponse response,
            String appName) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        OutputStream os = null;
        BufferedOutputStream buff = null;
        try {
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setContentType("application/plain");// 定义输出类型
            //String cssFileName = appName + "-ccs";
            String cssFileName = appName ;
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(cssFileName.getBytes("UTF-8"), "ISO8859_1") + ".properties");// 设定输出文件头
            String path = "";
            buff = new BufferedOutputStream(os);
            IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            export2buff(buff, path, configClient);
            buff.flush();
            buff.close();

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("导出文件时出错：", e);
        }
    }

    private void export2buff(BufferedOutputStream buff, String path,
    		IConfigClient configClient) throws IOException, UnsupportedEncodingException, ConfigException {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
    	List<String> pathList = configClient.listSubPath(path);
        String enter = "\r\n";
        if (!CollectionUtil.isEmpty(pathList)) {
            for (String subPath : pathList) {
                if (configClient.exists(path + "/" + subPath)) {
                    String value = configClient.get(path + "/" + subPath);
                    value=StringHelper.replaceBlank(value);
                    if (!StringUtil.isBlank(value)) {
                        String s = path + "/" + subPath + "=" + value + enter;
                        buff.write(s.getBytes("UTF-8"));
                    }
                    String subFullPath = path + "/" + subPath;
                    export2buff(buff, subFullPath, configClient);
                }
            }
        }
    }
 // 导出文件
    @RequestMapping("/downOneConfig")
    public void downOneConfig(HttpServletRequest request, HttpServletResponse response,
            String appName,String path) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String zkAddr = authInfo.getCcsZkAddress();
        OutputStream os = null;
        BufferedOutputStream buff = null;
        try {
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setContentType("application/plain");// 定义输出类型
            String cssFileName = appName + "-ccs";
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(cssFileName.getBytes("UTF-8"), "ISO8859_1") + ".properties");// 设定输出文件头
            buff = new BufferedOutputStream(os);
            IConfigClient configClient = CCSClientFactory.getConfigClientBySdkMode(appName, zkAddr);
            export2buff(buff, path, configClient);
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("导出文件时出错：", e);
        }
    }
}
