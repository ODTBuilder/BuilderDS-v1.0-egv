package com.git.qaproducer.user.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.repository.UserRepository;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("userService")
@Transactional
public class UserService extends EgovAbstractServiceImpl{

	@Resource(name="userRepository")
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public User retrieveUserById(String uid) {
		return userRepository.retrieveUserById(uid);
	}

	@Transactional(readOnly = true)
	public User retrieveUserByIdx(int idx) {
		return userRepository.retrieveUserByIdx(idx);
	}

	@Transactional
	public void createUser(User user) {
		userRepository.createUser(user);
	}

	@Transactional(readOnly = true)
	public User checkUserById(User user) {
		return userRepository.checkUserById(user);
	}

	@Transactional(readOnly = true)
	public User checkUserByEmail(User user) {
		return userRepository.checkUserByEmail(user);
	}

	@Transactional(readOnly = true)
	public User checkDuplicatedById(String uid) {
		return userRepository.checkDuplicatedById(uid);
	}

	@Transactional(readOnly = true)
	public User checkDuplicatedByEmail(String email) {
		return userRepository.checkDuplicatedByEmail(email);
	}

	@Transactional
	public boolean setActiveUserById(User user) {
		return userRepository.setActiveUserById(user);
	}

}
