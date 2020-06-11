package com.amatistah.TagsJSP.tags.supes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.amatistah.keys.AmatistaKeys;
import com.amatistah.model.User;
import com.amatistah.services.interfaces.MongoService;
import com.amatistah.servlet.supers.Request;

public abstract class TagManager extends TagSupport {

	private static final long serialVersionUID = -8028247990454892583L;
	private static MongoService mongo;

	@Autowired
	private void setMongo(MongoService mongo) {
		TagManager.mongo = mongo;
	}

	public static MongoService getDAO() {
		return mongo;
	}
	
	protected Request getRequest() {
		Request request = new Request((HttpServletRequest) pageContext.getRequest());
		return request;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.append(appendOut());
		} catch (IOException e) {
		}
		return SKIP_BODY;
	}

	protected abstract String appendOut();

	protected User getUser() {
		User user = (User) pageContext.getSession().getAttribute(AmatistaKeys.USER_ATTRIBUTE);
		return user;
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

}
