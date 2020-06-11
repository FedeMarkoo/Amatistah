package com.amatistah.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amatistah.keys.AmatistaKeys;
import com.amatistah.model.User;
import com.amatistah.services.interfaces.MongoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("userAcces")
public class UserAcces {

	@Autowired
	private MongoService dao;

	@Autowired
	ObjectMapper mapper;

	@GetMapping("/login")
	public void login(@RequestParam String userName, @RequestParam String password) {
		dao.validateUserLogin(userName, password);
	}

	@PostMapping("/signUp")
	public String signUp(@RequestParam String userName, @RequestParam String email, @RequestParam String password) {
		dao.validateUserLogin(userName, password);
		return "asd";
	}

	@PostMapping("/signGoogle")
	public @ResponseBody ObjectNode signGoogle(@RequestParam String userName, @RequestParam String email,
			HttpServletRequest request) {
		ObjectNode node = mapper.createObjectNode();
		try {
			User user = (User) request.getSession().getAttribute(AmatistaKeys.USER_ATTRIBUTE);
			if (user == null) {
				user = dao.getUser(userName);
				if (user == null) {
					user = new User();
					user.setUserName(userName);
					user.setEmail(email);
					dao.registerUser(user);
				}
				request.getSession().setAttribute(AmatistaKeys.USER_ATTRIBUTE, user);
			}
			node.put("statusCode", 200);
		} catch (Exception e) {
			node.put("statusCode", 500);
		}
		 return node;
	}

}
