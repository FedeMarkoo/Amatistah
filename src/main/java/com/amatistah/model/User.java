package com.amatistah.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

	private String userName;
	private String password;
	private Boolean passwordReseted;
	private String email;
	private ArrayList<Integer> menus;

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

	public Boolean getPasswordReseted() {
		return passwordReseted;
	}

	public ArrayList<Integer> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Integer> menus) {
		this.menus = menus;
	}
	
	public void setPasswordReseted(Boolean passwordReseted) {
		this.passwordReseted = passwordReseted;
	}

	public void addMenu(Integer menu) {
		if (this.menus == null) {
			this.menus = new ArrayList<>();
		}
		this.menus.add(menu);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
