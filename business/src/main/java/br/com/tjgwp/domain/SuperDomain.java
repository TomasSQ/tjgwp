package br.com.tjgwp.domain;

import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.user.UserEntity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class SuperDomain {

	static {
		factory().register(SuperEntity.class);
		factory().register(UserEntity.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

	public static Key<?> save(Object o) {
		return ofy().save().entity(o).now();
	}

}
