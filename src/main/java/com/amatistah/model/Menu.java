package com.amatistah.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Menus")
public class Menu {

	private int menuID;
	private String descript;
	private String url;
	private ArrayList<Menu> childrenMenus;

	public Menu() {
	}
	
	public int getMenuID() {
		return menuID;
	}
	
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}

	public Menu(String descript, String url) {
		this.descript = descript;
		this.url = url;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<Menu> getChildrenMenus() {
		return childrenMenus;
	}

	public void setChildrenMenus(ArrayList<Menu> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}
}
