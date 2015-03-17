package br.com.tjgwp.domain.user;

import java.util.List;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.entity.user.UserHistory;
import br.com.tjgwp.business.utils.URLMaker;
import br.com.tjgwp.domain.SuperDomain;

import com.google.appengine.repackaged.org.joda.time.DateTime;

public class UserDomain extends SuperDomain {

	public List<UserEntity> findByEmail(String email) {
		return ofy().load().type(UserEntity.class).filter("email", email).list();
	}

	public List<UserHistory> getLastedHistoryFromUser(UserEntity user) {
		return ofy().load().type(UserHistory.class).order("-date").limit(10).filter("date >", DateTime.now().minusDays(10).getMillis()).ancestor(user).list();
	}

	public void removeChapterUserHistory(UserEntity userEntity, Book book, Chapter chapter) {
		removeAll(userEntity, ofy().load().type(UserHistory.class).ancestor(userEntity).filter("url", URLMaker.urlForChapter(chapter)).list());
	}

	public void removeBookUserHistory(UserEntity userEntity, Book book) {
		removeAll(userEntity, ofy().load().type(UserHistory.class).ancestor(userEntity).filter("url", URLMaker.urlForBook(book)).list());
	}

	public UserEntity findById(Long id) {
		return findById(id, UserEntity.class);
	}

}
