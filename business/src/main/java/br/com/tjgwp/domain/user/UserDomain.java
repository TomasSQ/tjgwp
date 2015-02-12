package br.com.tjgwp.domain.user;

import java.util.List;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.entity.user.UserHistory;
import br.com.tjgwp.domain.SuperDomain;

import com.google.appengine.repackaged.org.joda.time.DateTime;

public class UserDomain extends SuperDomain {

	public List<UserEntity> findByEmail(String email) {
		return ofy().load().type(UserEntity.class).filter("email", email).list();
	}

	public List<UserHistory> getLastedHistoryFromUser(UserEntity user) {
		return ofy().load().type(UserHistory.class).limit(10).filter("date >", DateTime.now().minusDays(10).getMillis()).ancestor(user).list();
	}

}
