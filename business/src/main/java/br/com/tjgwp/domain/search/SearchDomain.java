package br.com.tjgwp.domain.search;

import java.util.List;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.domain.SuperDomain;

public class SearchDomain extends SuperDomain {

	public List<UserEntity> searchUser(String word) {
		return ofy().load().type(UserEntity.class).filter("nickname >=", word).filter("nickname <", word + '\uFFFD').list();
	}

}
