package com.amatistah.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.amatistah.servlet.supers.Servlet;

/**
 * Servlet implementation class Main
 */
@Controller
public class Amatista extends Servlet{
	
	private static final long serialVersionUID = -6125446333512609850L;

	public Amatista() {
		super("/views/main.jsp");
	}

	@GetMapping("/ama.tista")
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//super.setBody(request, response);
		return "views/main";
	}
	
	
}
