package com.amatistah.TagsJSP.tags;

import java.util.List;

import com.amatistah.TagsJSP.tags.supes.TagManager;
import com.amatistah.model.Menu;

public class MenusToggleTag extends TagManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3270389891773946388L;

	protected String appendOut() {
		List<Menu> menuList = getDAO().getMenuList();
		String code = "<div class=\"btn-group-vertical\">\r\n";
		for (Menu menu : menuList) {
			code += generateCodeForMenuItem(menu);
		}
		code += "\r\n</div>";

		return code;
	}

	private String generateCodeForMenuItem(Menu menu) {
		return "  <input type=\"radio\" id=\""+menu.getUrl()+"\" name=\""+menu.getUrl()+"\" value=\""+menu.getUrl()+"\"><label for=\""+menu.getUrl()+"\">"+menu.getDescript()+"</label>\r\n";
	}

}
