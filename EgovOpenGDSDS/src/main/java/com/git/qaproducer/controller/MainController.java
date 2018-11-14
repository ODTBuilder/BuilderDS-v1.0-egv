package com.git.qaproducer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.git.qaproducer.common.security.LoginUser;

@Controller
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);



	@RequestMapping(value = "/{locale:en|ko}/locale.do", method = RequestMethod.GET)
	public String localeMainView(HttpServletRequest request, @AuthenticationPrincipal LoginUser loginUser) {
		LOGGER.info("access: /locale.do");
		String redir = "redirect:/main.do";
		return redir;
	}

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public ModelAndView mainView(HttpServletRequest request, @AuthenticationPrincipal LoginUser loginUser) {
		LOGGER.info("access: /main.do");
		ModelAndView mav = new ModelAndView();
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUsername());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		mav.setViewName("/main/main");
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		return mav;
	}

	@RequestMapping(value = "/map.do", method = RequestMethod.GET)
	public ModelAndView mapView(HttpServletRequest request, @AuthenticationPrincipal LoginUser loginUser) {
		LOGGER.info("access: /map.do");
		ModelAndView mav = new ModelAndView();
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUsername());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		mav.setViewName("/map/map");
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		return mav;
	}


	@RequestMapping(value = "/settinglist.do", method = RequestMethod.GET)
	public ModelAndView settingListView(HttpServletRequest request, @AuthenticationPrincipal LoginUser loginUser) {
		LOGGER.info("access: /settinglist.do");
		LOGGER.info("login user: {}.", loginUser);
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", loginUser.getUsername());
		mav.addObject("fname", loginUser.getFname());
		mav.addObject("lname", loginUser.getLname());
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		mav.setViewName("/user/settinglist");
		return mav;
	}


	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView errListView(HttpServletRequest request, @AuthenticationPrincipal LoginUser loginUser) {
		LOGGER.info("access: /list.do");
		LOGGER.info("login user: {}.", loginUser);
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", loginUser.getUsername());
		mav.addObject("fname", loginUser.getFname());
		mav.addObject("lname", loginUser.getLname());
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		// LinkedList<ValidationResult> list = validationResultService.retrieveValidationResultByUidx(loginUser.getIdx());
		// mav.addObject("list", list);
		mav.setViewName("/user/list");
		return mav;
	}

}