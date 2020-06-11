package com.amatistah.services.interfaces;

import java.util.List;

import com.amatistah.model.Menu;
import com.amatistah.model.User;

public interface MongoService {

	public List<Menu> getMenus();

	public boolean validateUserLogin(String userName, String password);

	public User getUser(String userName);

	public void registerUser(User user);

	void registerMenu(Menu menu);

	public List<Menu> getMenusForUser(User user);

	public List<Menu> getMenuList();
	
}
