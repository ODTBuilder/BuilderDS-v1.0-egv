package com.git.qaproducer.user.mapper;

import java.util.Map;

import com.git.qaproducer.user.domain.User;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("userMapper")
public interface UserMapper {
	
	User loginUserByInfo(Map<String, Object> infoMap);
	
	User retrieveUserById(String uid);

	User retrieveUserByIdx(int idx);

	void createUser(User user);

	User checkUserById(User user);

	User checkDuplicatedById(String uid);

	User checkDuplicatedByEmail(String email);

	User checkUserByEmail(User user);

	boolean setActiveUserById(User user);
}
