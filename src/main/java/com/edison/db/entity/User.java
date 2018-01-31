package com.edison.db.entity;

import org.springframework.stereotype.Repository;

@Repository
public class User {
	private String userName;
	
	private String password;
	
	private String sex;
	
	private String city;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password
				+ ", sex=" + sex + "]";
	}

	
}
