package com.ai.baas.mt.web.controller.configcenter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ai.baas.mt.web.constants.BaaSMTConstants;
import com.ai.baas.mt.web.model.LoginUserBean;
import com.ai.baas.mt.web.util.ConfigUtil;
import com.ai.opt.sdk.util.StringUtil;

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);


    /**
     * 配置加载查询
     * 
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response,LoginUserBean user) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		StringBuffer s=new StringBuffer();
		
		try{
			
			//获取页面参数
			String username=user.getUsername()!=null?user.getUsername().toString().trim():"";
			String userpwd=user.getPassword()!=null?user.getPassword().toString().trim():"";
			
			if(username==null||"".equals(username))
			{
				LOG.info(" 账号名为空");
				s.append("<script>location.href='login.jsp?login_error=nulluser';</script>");
				writer.println(s.toString());
				return null;
			}
			if(userpwd==null||"".equals(userpwd))
			{
				LOG.info(" 密码为空");
				s.append("<script>location.href='login.jsp?login_error=nullpwd';</script>");
				writer.println(s.toString());
				return null;
			}
			
			String authUserPwd=ConfigUtil.getProperty(username);
			if(StringUtil.isBlank(authUserPwd)){//账号名不存在
				
				LOG.info(" 账号名不存在");
				s.append("<script>location.href='login.jsp?login_error=nouser';</script>");
				writer.println(s.toString());
				return null;
			}
			
			if(!authUserPwd.equalsIgnoreCase(userpwd))//账号未激活
			{
				LOG.info(" 密码错误");
				
				s.append("<script>location.href='login.jsp?username="+username+"&login_error=pwderror';</script>");
				writer.println(s.toString());
				return null;
			}
			
			
			
			LOG.info(" 登录成功！user.username="+user.getUsername());
			//登录成功，存入Session
			request.getSession().setAttribute(BaaSMTConstants.USER_SESSION_KEY, user);
			//forward = "loading";//转向网站主界面。
			
			s.append("<script>location.href='index.jsp';</script>");
			writer.println(s.toString());
			return null;	
			
			//return forward;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			s.append("<script>location.href='login.jsp?login_error=unknown_error';</script>");
			writer.println(s.toString());
			LOG.debug(" 登录失败！");
			return null;			
		}
    }

    
}
