package com.amatistah.TagsJSP.tags;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amatistah.TagsJSP.tags.supes.TagManager;
import com.amatistah.model.Menu;
import com.amatistah.model.User;

@Component
public class MenuTag extends TagManager {

	private static final long serialVersionUID = 3123913682669081766L;

	public String appendOut() {
		return setMenuButtons();
	}

	private String setMenuButtons() {
		User user = (User) super.getSession().getAttribute("user");
		List<Menu> menusForUser = new ArrayList<Menu>();
		if (user != null)
			menusForUser = getDAO().getMenusForUser(user);
		List<Menu> menus = menusForUser;
		String buttonsCode = "<div class=\"btn-group\">";
		for (Menu menu : menus) {
			buttonsCode += generateButtonCode(menu);
		}
		return buttonsCode + "</div>";
	}

	private String generateButtonCode(Menu menu) {
		if (menu.getDescript().equals("editPage")) {
			return "<input class=\"editorButton\" type=\"button\" value=\"" + menu.getDescript() + "\" " + "onclick=\""
					+ menu.getUrl() + "()\"/>";
		}
		ArrayList<Menu> childrenMenus = menu.getChildrenMenus();
		if (childrenMenus != null)
			return generateCodeForDropdown(menu);
		else
			return generateCodeForButton(menu);
	}

	private String generateCodeForButton(Menu menu) {
		return "<a " 
				+ "class=\"btn btn-primary btn-lg active\"" 
				+ "href=\"/" + menu.getUrl() + "\"" 
				+ "role=\"button\""
				+ "aria-pressed=\"true\"" 
				+ "id=\""+ menu.getUrl() + "\">" 
				+ menu.getDescript() 
				+ "</a>";
	}

	private String generateCodeForDropdown(Menu menu) {
		return "<div class=\"btn-group\">\r\n" + "  <button onclick=\"location.href = '" + menu.getUrl()
				+ "';\"  type=\"button\" class=\"btn btn-primary\">" + menu.getDescript() + "</button>\r\n"
				+ "  <button type=\"button\" class=\"btn btn-primary dropdown-toggle dropdown-toggle-split\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\r\n"
				+ "    <span class=\"sr-only\">" + menu.getDescript() + "</span>\r\n" + "  </button>\r\n"
				+ "  <div class=\"dropdown-menu\">\r\n" + "    " + generateSubMenus(menu.getChildrenMenus()) + "\r\n"
				+ "  </div>\r\n" + "</div>";
	}

	protected String generateSubMenus(ArrayList<Menu> arrayList) {
		String subs = "";
		for (Menu menu : arrayList) {
			subs += "<a class=\"dropdown-item\" href=\"" + menu.getUrl() + "\">" + menu.getDescript() + "</a>";
		}
		return subs;
	}
}
