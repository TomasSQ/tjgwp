package br.com.tjgwp.domain.user;

import java.util.List;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.domain.SuperDomain;

public class UserDomain extends SuperDomain {

	public static List<UserEntity> findUsersByEmail(String email) {
		return ofy().load().type(UserEntity.class).filter("email", email).list();
	}

}
