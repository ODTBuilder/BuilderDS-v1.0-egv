package com.git.qaproducer.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

@Controller
public class MainController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);



	@RequestMapping(value = "/{locale:en|ko}/locale.do", method = RequestMethod.GET)
	public String localeMainView(HttpServletRequest request) {
		LOGGER.info("access: /locale.do");
		String redir = "redirect:/main.do";
		return redir;
	}

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public ModelAndView mainView(HttpServletRequest request) {
		LOGGER.info("access: /main.do");
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
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
	public ModelAndView mapView(HttpServletRequest request) {
		LOGGER.info("access: /map.do");
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
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
	public ModelAndView settingListView(HttpServletRequest request) {
		LOGGER.info("access: /settinglist.do");
		
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		LOGGER.info("login user: {}.", loginUser);
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
	public ModelAndView errListView(HttpServletRequest request) {
		LOGGER.info("access: /list.do");
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		LOGGER.info("login user: {}.", loginUser);
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
