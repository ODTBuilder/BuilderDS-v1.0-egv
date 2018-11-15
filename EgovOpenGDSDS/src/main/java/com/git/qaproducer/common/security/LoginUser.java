package com.git.qaproducer.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;


public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private int idx;
	private String email;
	private String fname;
	private String lname;
	private int aid;
	private Boolean active;

	
	public LoginUser(com.git.qaproducer.user.domain.User user) {
		super(user.getUid(), user.getPw(), AuthorityUtils.createAuthorityList(user.getAuth()));
		this.idx = user.getIdx();
		this.active = user.getActive();
		this.email = user.getEmail();
		this.fname = user.getFname();
		this.lname = user.getLname();
		this.aid = user.getAid();
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
