package com.sale.services;

import java.util.ArrayList;

import com.sale.dao.UserDao;
import com.sale.model.Users;

public class UserServices {
	public UserDao userDao=new UserDao();
	
	public ArrayList<Users> showUser() {
		return userDao.showUser();
	}	
	public Users createUser(Users user) {
		return userDao.createUser(user);
	}
	public Users updateUser(Users user){
		return userDao.updateUser(user);
	}	
}
