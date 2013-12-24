package br.com.tjgwp.business.service.user;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.users.UserServiceFactory;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.Service;

public class UserService extends Service {

	public UserEntity getLoggedUser() {
		return new UserEntity(getUser());
	}

	public String getLoginUrl(HttpServletRequest req) {
		if (getUser() == null)
			return UserServiceFactory.getUserService().createLoginURL(req.getRequestURI());
		return null;
	}

}
