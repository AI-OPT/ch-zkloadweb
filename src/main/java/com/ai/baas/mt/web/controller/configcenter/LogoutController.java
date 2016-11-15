package com.ai.baas.mt.web.controller.configcenter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.baas.mt.web.constants.BaaSMTConstants;


@Controller
public class LogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		try {
			session.removeAttribute(BaaSMTConstants.USER_SESSION_KEY);
			session.invalidate();
			String _base=request.getContextPath();
			response.sendRedirect(_base);
		} catch (IOException e) {
			LOG.error("用户登出失败",e);
		}
	}

}
