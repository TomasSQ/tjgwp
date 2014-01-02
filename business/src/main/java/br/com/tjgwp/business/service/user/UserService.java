package br.com.tjgwp.business.service.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.Service;
import br.com.tjgwp.domain.user.UserDomain;

public class UserService extends Service {

	public UserEntity getLoggedUser() {
		User user = getUser();
		if (user == null)
			return new UserEntity(user);

		List<UserEntity> users = UserDomain.findUsersByEmail(user.getEmail());
		if (users.isEmpty()) {
			UserEntity userEntity = new UserEntity(user);
			UserDomain.save(userEntity);

			return userEntity;
		}

		return users.get(0);
	}

	public String getLoginUrl(HttpServletRequest req) {
		if (getUser() == null)
			return UserServiceFactory.getUserService().createLoginURL("/");

		return "";
	}

	public String getLogoutUrl(HttpServletRequest req) {
		if (getUser() != null)
			return UserServiceFactory.getUserService().createLogoutURL("/");

		return "";
	}

}
