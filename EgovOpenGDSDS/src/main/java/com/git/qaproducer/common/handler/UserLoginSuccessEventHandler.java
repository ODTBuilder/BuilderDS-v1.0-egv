package com.git.qaproducer.common.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;
import com.git.qaproducer.common.security.LoginUser;

@Component
public class UserLoginSuccessEventHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	Logger logger = LoggerFactory.getLogger(UserLoginSuccessEventHandler.class);

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (authentication != null && authentication.getDetails() != null) {
			LoginUser user = (LoginUser) authentication.getPrincipal();
			logger.info("Login Success... - {}", user);
			HttpSession httpSession = request.getSession();
			
			
			//Geoserver 세션 추가
			DTGeoserverManagerList dtGeoserverList = new DTGeoserverManagerList();
			httpSession.setAttribute("geoserver", dtGeoserverList);
			
			 //set our response to OK status
			response.setStatus(HttpServletResponse.SC_OK);
	 
	        //since we have created our custom success handler, its up to us to where
	        //we will redirect the user after successfully login
			response.sendRedirect(request.getContextPath()+"/map.do");
		}
	}
}