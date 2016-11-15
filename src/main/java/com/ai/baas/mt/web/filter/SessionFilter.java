package com.ai.baas.mt.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ai.baas.mt.web.constants.BaaSMTConstants;
import com.ai.opt.sdk.util.StringUtil;


public class SessionFilter implements Filter {
	public final Logger logger = Logger.getLogger(getClass());
	private  String[] ignore_resources;
	private FilterConfig currentFilterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.currentFilterConfig=filterConfig;
		String exclude = currentFilterConfig.getInitParameter("ignore_resources");
		if(exclude!=null){
			ignore_resources = exclude.split(",");
		}
		
	}
	@Override
	public void destroy() {		

	}
	private  boolean isIgnored(String requestUrl) {
		if (ignore_resources == null){
			return false;
		}else{
			for (String suffix : ignore_resources) {
				if (!StringUtils.isBlank(suffix)&&requestUrl.endsWith(suffix.trim().toLowerCase())) {
					return true;
				}
			} 
			return false;
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);
		response.setContentType("text/html;charset=UTF-8");/*指定文档类型及页面编码*/
		
		logger.debug("Now in SessionFilter.java");
		logger.debug("JVM MAX MEMORY: " + Runtime.getRuntime().maxMemory()/1024/1024+"M");
		logger.debug("JVM IS USING MEMORY:" + Runtime.getRuntime().totalMemory()/1024/1024+"M");
		logger.debug("JVM IS FREE MEMORY:" + Runtime.getRuntime().freeMemory()/1024/1024+"M");
		/**
		 * 1,doFilter方法的第一个参数为ServletRequest对象。此对象给过滤器提供了对进入的信息（包括
		 * 表单数据、cookie和HTTP请求头）的完全访问。第二个参数为ServletResponse，通常在简单的过
		 * 滤器中忽略此参数。最后一个参数为FilterChain，此参数用来调用servlet或JSP页。
		 */

		/**
		 * 如果处理HTTP请求，并且需要访问诸如getHeader或getCookies等在ServletRequest中
		 * 无法得到的方法，就要把此request对象构造成HttpServletRequest
		 */
		
		logger.debug("request.getContextpath="+request.getContextPath());
		String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
		logger.debug("currentURL=" + currentURL);
		String targetURL =currentURL.replace(request.getContextPath(), "");	//如果项目发布时有项目名，则需要剔除项目明后再比较
		logger.debug("targetURL=" + targetURL);		
		
		//排除不用拦截的url
		//boolean authPassFlag = appUrlFilterRule(targetURL);
		boolean authPassFlag = isIgnored(targetURL.toLowerCase());
		

		//if (!"/login.action".equals(targetURL)&&!"/login.jsp".equals(targetURL)) {
		//if (!targetURL.startsWith("/login.action")&&!targetURL.startsWith("/login.jsp")&&!targetURL.startsWith("/services")) {	
		if (!authPassFlag) {	
			
			// 判断当前页是否是重定向以后的登录页面(session.jsp)页面，如果是就不做session的判断，防止出现死循环

			if (session == null || session.getAttribute(BaaSMTConstants.USER_SESSION_KEY) == null) {
				// *用户登录以后需手动添加session
				
				/*System.out.println("request.getContextPath()="
						+ request.getContextPath());*/
				
				// 如果session为空表示用户没有登录就重定向到/session.jsp页面
				
				/**
				 * 一般使用filter过滤用户是否登录，如果用户没有登陆则转向登陆页面，
				 * 这时候可以使用response.sendRedirect()。但当在页面上使用了frameset或iframe后，
				 * 发现被重定向的只是父页面中的iframe或frame区域，登陆页面内容显示在该区域中。
				 * 说明在过滤器中发送重定向请求时，是在iframe或frame页面发送的。错误的代码如下：
				 * response.sendRedirect(request.getContextPath() + "/session.jsp");
				 *
				 *  因为response.sendRedirect()没有target属性，但html页面和js中有，
				 *  于是，当判断出用户没有访问权限时，我们可以在jsp中使用js来转向到真正的登录页面。
				 *  在filter类的doFilter方法中添加如下代码:
				 */
				
				/**
				 * 判断访问者是电脑还是手机。
				 * 如果是电脑，转向/session.jsp, 验证单点登录；
				 * 如果是手机，转向/login.jsp, 跳过单点登录，直接转向登录页面。
				 */
				int random=new Random().nextInt();
				random*=1000;
				logger.debug("request.getHeader(\"user-agent\")="+request.getHeader("user-agent"));
				String forwardUrl="/login.jsp"; 

				logger.debug("forwardUrl="+forwardUrl);
				
				PrintWriter out = response.getWriter();   
				/*推荐使用此种页面重定向方法*/
				out.println("<html>");
				out.println("<script>");
				out.println("window.top.location.href='"+request.getContextPath()+forwardUrl+"';");   
				out.println("</script>");  
				out.println("</html>");   
				
				/*另一种页面重定向的方法
				 * out.println("<html>");
				out.println("<script>");
				out.println("window.open ('"+request.getContextPath()+forwardUrl+"','_top')");
				out.println("</script>");  
				out.println("</html>");   */
				
				return;
			}
		}
		
		// 加入filter链继续向下执行
		filterChain.doFilter(request, response);
		/**
		 * 调用FilterChain对象的doFilter方法。Filter接口的doFilter方法取一个FilterChain对象作 为它
		 * 的一个参数。在调用此对象的doFilter方法时，激活下一个相关的过滤器。如果没有另
		 * 一个过滤器与servlet或JSP页面关联，则servlet或JSP页面被激活。
		 */

	}

	private boolean appUrlFilterRule(String targetURL) throws IOException {
		Properties prop = new Properties();
		InputStream inStream = SessionFilter.class.getClassLoader().getResourceAsStream("appurl.properties");
		prop.load(inStream);
		String excludeUrls=prop.getProperty("excludeUrls");
		if(StringUtil.isBlank(excludeUrls)){
			excludeUrls="/login.action,/login.jsp,/services";
		}
		String[] nonfilter=excludeUrls.split(",");
		boolean authPassFlag=false;
		for(int i=0;i<nonfilter.length;i++){
			if(targetURL.startsWith(nonfilter[i])){
				authPassFlag=true;
				break;
			}
		}
		return authPassFlag;
	}

	

}
