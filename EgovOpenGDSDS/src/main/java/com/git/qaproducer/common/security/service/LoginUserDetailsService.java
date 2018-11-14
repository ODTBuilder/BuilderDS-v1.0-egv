package com.git.qaproducer.common.security.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.git.qaproducer.common.security.LoginUser;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.service.UserService;

@Service("loginUserDetailsService")
public class LoginUserDetailsService implements UserDetailsService {

	@Resource(name="userService")
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		// 기존해시와 신규해시가 다를경우 이런식으로 받아 처리할 수 있음.
		// 위 @Autowired HttpServletRequest request;
		// request 처리

		User user = userService.retrieveUserById(uid);

		if (user == null) {
			// 계정이 존재하지 않음
			throw new UsernameNotFoundException("login fail");
		}

		return new LoginUser(user);
	}

}
