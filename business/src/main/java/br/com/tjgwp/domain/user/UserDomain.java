package br.com.tjgwp.domain.user;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.entity.user.UserHistory;
import br.com.tjgwp.domain.SuperDomain;

public class UserDomain extends SuperDomain {

	public List<UserEntity> findByEmail(String email) {
		return ofy().load().type(UserEntity.class).filter("email", email).list();
	}

	public List<UserHistory> getLastedHistoryFromUser(UserEntity user) {
		return ofy().load().type(UserHistory.class).limit(10).filter("date >", Instant.now().minus(10, ChronoUnit.DAYS).toEpochMilli()).ancestor(user).list();
	}

}
