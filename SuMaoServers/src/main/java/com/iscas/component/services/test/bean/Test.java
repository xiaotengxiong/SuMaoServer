package com.iscas.component.services.test.bean;

import com.iscas.component.core.PagerModel;

public class Test extends PagerModel {
	private String username;
	private String password;
	private String status;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "username:" + username + ",password:" + password + ",status: " + status;
	}
}
