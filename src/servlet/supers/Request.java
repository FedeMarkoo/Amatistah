package servlet.supers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import data.hibernate.BDManager;
import data.objects.Menu;

public class Request extends HttpServletRequestWrapper {

	private boolean hasToRedirect;

	public Request(HttpServletRequest request) {
		super(request);
		setMenuButtons(request);
	}

	private void setMenuButtons(HttpServletRequest request) {
		ArrayList<Menu> menus = getMenuForUser(request);
		String buttonsCode = "";
		for (Menu menu : menus) {
			buttonsCode += generateButtonCode(menu);
		}
		request.getSession().setAttribute("menuButtons", buttonsCode);
	}

	private String generateButtonCode(Menu menu) {
		if (menu.getDescript().equals("editPage")) {
			return "<input class=\"editorButton\" type=\"button\" value=\"" + menu.getDescript() + "\" " + "onclick=\""
					+ menu.getDestination() + "()\"/>";
		}
		return "<input class=\"menuButton\" type=\"button\" value=\"" + menu.getDescript() + "\" "
				+ "onclick=\"location.href = '" + menu.getDestination() + "'\"/>";
	}

	private ArrayList<Menu> getMenuForUser(HttpServletRequest request) {
		BDManager bdManager = new BDManager();
		try {
			return (ArrayList<Menu>) bdManager.getObject("Menu");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer getParameterAsInteger(String name) {
		return Integer.valueOf(super.getParameter(name));
	}

	public Long getParameterAsLong(String name) {
		return Long.valueOf(super.getParameter(name));
	}

	public boolean getParameterAsBoolean(String name) {
		return Boolean.valueOf(super.getParameter(name));
	}

	public String getParameterAsString(String name) {
		return super.getParameter(name);
	}

	public boolean existParameter(String name) {
		return super.getParameter(name) != null;
	}

	public boolean hasParameters() {
		return super.getParameterMap().size() == 0;
	}

	public boolean hasToRedirect() {
		return hasToRedirect;
	}

	public void setHasToRedirect(boolean hasToRedirect) {
		this.hasToRedirect = hasToRedirect;
	}

}
