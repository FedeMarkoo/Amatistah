package com.amatistah.utils;

import com.amatistah.model.User;

public class AmatistaUtil {

	public boolean isUserLogged() {
		return this.getCurrentUser() != null;
	}

	private User getCurrentUser() {
		return null;
	}
	
}
