package br.com.tjgwp.business.service;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class SuperService {

	public User getUser() {
		return UserServiceFactory.getUserService().getCurrentUser();
	}

}
