package com.amatistah.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.amatistah.keys.AmatistaKeys;

/**
 * Servlet Filter implementation class TemplateFilter
 */
//@WebFilter("/*")
//@Component
public class TemplateFilter implements Filter {
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//if (!((HttpServletRequest) request).getRequestURI().contains("templates/"))
		//	request.getRequestDispatcher(AmatistaKeys.TEMPLATES_HEADER).forward(request, response);

		chain.doFilter(request, response);
	}

}
