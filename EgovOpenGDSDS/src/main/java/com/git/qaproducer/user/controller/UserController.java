package com.git.qaproducer.user.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.git.qaproducer.common.security.LoginUser;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource(name="userService")
	UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	
	//로그인
	@RequestMapping(value = "/signin.do", method = RequestMethod.GET)
	public String signinView(HttpServletRequest request, Principal principal) {
		LOGGER.info("access: /signin.do");
		String redir;
		LoginUser loginUser = (LoginUser) principal;
		if (loginUser != null) {
			redir = "redirect:main.do";
		} else {
			redir = "/user/signin";
		}
		return redir;
	}

	
	//가입
	@RequestMapping(value = "/signup.do", method = RequestMethod.POST)
	public String signupProcess(@RequestParam("email") String email, @RequestParam("userid") String uid,
			@RequestParam("firstname") String fname, @RequestParam("lastname") String lname,
			@RequestParam("password") String pw) {
		LOGGER.info("access: /signup.do, user: {}.", uid);
		User user = new User();
		user.setUid(uid);
		user.setPw(new BCryptPasswordEncoder().encode(pw));
		user.setEmail(email);
		user.setAid(1);
		user.setFname(fname);
		user.setLname(lname);
		userService.createUser(user);
		return "redirect:signin.do";
	}

	@RequestMapping(value = "/userinfo.do")
	@ResponseBody
	public ModelAndView userInfoView(HttpServletRequest request, Principal principal)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		LoginUser loginUser = (LoginUser) principal;
		if (loginUser != null) {
			LOGGER.info("access: /userinfo.do user={}", loginUser.getUsername());
			mav.addObject("username", loginUser.getUsername());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
			mav.addObject("email", loginUser.getEmail());
			mav.addObject("auth", loginUser.getAuthorities());
		}
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		mav.setViewName("/user/userinfo");
		return mav;
	}

	@RequestMapping(value = "/idcheck.ajax")
	@ResponseBody
	public boolean checkDuplicatedID(HttpServletRequest request) throws Exception {
		boolean isUnique = false;
		String id = request.getParameter("id");
		User oldUser = userService.checkDuplicatedById(id);
		if (oldUser == null) {
			isUnique = true;
		}
		LOGGER.info("아이디 중복확인:{}", id);
		LOGGER.info("유일 아이디 여부:{}", isUnique);
		return isUnique;
	}

	@RequestMapping(value = "/emailcheck.ajax")
	@ResponseBody
	public boolean checkDuplicatedEmail(HttpServletRequest request) throws Exception {
		boolean isUnique = false;
		String email = request.getParameter("email");
		User oldUser = userService.checkDuplicatedByEmail(email);
		if (oldUser == null) {
			isUnique = true;
		}
		LOGGER.info("이메일 중복확인:{}", email);
		LOGGER.info("유일 아이디 여부:{}", isUnique);
		return isUnique;
	}

	@RequestMapping(value = "/deactivateuser.ajax")
	@ResponseBody
	public boolean deactivateUser(HttpServletRequest request, Principal principal)
			throws Exception {
		LoginUser loginUser = (LoginUser) principal;
		LOGGER.info("유저 비활성화 user=:{}", loginUser.getUsername());
		boolean isDeactivated = false;
		User user = new User();
		user.setUid(loginUser.getUsername());
		user.setActive(false);
		isDeactivated = userService.setActiveUserById(user);
		return isDeactivated;
	}
}
