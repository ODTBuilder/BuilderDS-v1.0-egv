package com.git.qaproducer.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;
import com.git.qaproducer.user.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

	@Resource(name="userService")
	UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	
	
	@RequestMapping(value = "/signinProcess.do", method = RequestMethod.POST)
	public String loadUserByUsername(HttpServletRequest request, @RequestParam("username") String userid, @RequestParam("password") String pw) {
		LOGGER.info("access: /signinProcess.do");
		String redir =""; 
		Map<String,Object> loginInfo = new HashMap<String,Object>();
		loginInfo.put("uid", userid);
		loginInfo.put("pw", pw);
		User loginUser = userService.loginUserByInfo(loginInfo);
		if (loginUser != null) {
			DTGeoserverManagerList dtGeoserverList = new DTGeoserverManagerList();
			setSession(request,"geoserver", dtGeoserverList);
			setSession(request, EnUserType.GENERAL.getTypeName(), loginUser);
			redir = "redirect:/main.do";
		} else {
			redir = "/user/signin";
		}
		return redir;
	}
	
	
	//로그인
	@RequestMapping(value = "/signin.do", method = RequestMethod.GET)
	public String signinView(HttpServletRequest request) {
		LOGGER.info("access: /signin.do");
		String redir;
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			redir = "redirect:/main.do";
		} else {
			redir = "/user/signin";
		}
		return redir;
	}

	/**
	 * 로그아웃 요청을 처리한다.
	 * @author SG.Lee
	 * @data 2017.07
	 * @param request - 클라이언트의 요청과 관련된 정보와 동작을 가지고 있는 객체
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/signout.do")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		User user = (User) getSession(request, EnUserType.GENERAL.getTypeName());

		if (user != null) {
			removeSession(request, "geoserver");
			removeSession(request, EnUserType.GENERAL.getTypeName());
		}

		mav.setViewName("redirect:/main.do");
		return mav;
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
	public ModelAndView userInfoView(HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			LOGGER.info("access: /userinfo.do user={}", loginUser.getUid());
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
			mav.addObject("email", loginUser.getEmail());
			mav.addObject("auth", loginUser.getAuth());
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
	public boolean deactivateUser(HttpServletRequest request)
			throws Exception {
		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		LOGGER.info("유저 비활성화 user=:{}", loginUser.getUid());
		boolean isDeactivated = false;
		User user = new User();
		user.setUid(loginUser.getUid());
		user.setActive(false);
		isDeactivated = userService.setActiveUserById(user);
		return isDeactivated;
	}
}
