package com.amatistah.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amatistah.model.Menu;
import com.amatistah.model.User;
import com.amatistah.services.interfaces.MongoService;

@Repository
@Transactional
public class MongoServiceImpl implements MongoService {

	@Autowired
	private MongoTemplate managerDAO;
	
	@Override
	public List<Menu> getMenus() {
		return managerDAO.findAll(Menu.class);
	}

	@Override
	public List<Menu> getMenuList() {
		return managerDAO.findAll(Menu.class);
	}

	@Override
	public boolean validateUserLogin(String userName, String password) {
		Query criteria = new Query().addCriteria(Criteria.where("userName").is(userName));
		List<User> find = managerDAO.find(criteria, User.class);
		return !find.isEmpty();
	}

	@Override
	public User getUser(String userName) {
		Query criteria = new Query().addCriteria(Criteria.where("userName").is(userName));
		return managerDAO.findOne(criteria, User.class);
	}
	
	@Override
	public void registerUser(User user) {
		managerDAO.insert(user);
	}
	
	@Override
	public void registerMenu(Menu menu) {
		managerDAO.insert(menu);
	}

	@Override
	public List<Menu> getMenusForUser(User user) {
		Query query = new Query().addCriteria(Criteria.where("menuID").in(user.getMenus()));
		return managerDAO.find(query, Menu.class);
	}
}
