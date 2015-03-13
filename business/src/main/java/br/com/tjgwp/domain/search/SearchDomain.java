package br.com.tjgwp.domain.search;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.domain.SuperDomain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

public class SearchDomain extends SuperDomain {

	public List<UserEntity> searchUser(String word) {
		return ofy().load().type(UserEntity.class).filter("nickname >=", word).filter("nickname <", word + '\uFFFD').list();
	}

	public List<Book> searchBook(String word) {
		List<Book> books = ofy().load().type(Book.class).filter("title >=", word).filter("title <", word + '\uFFFD').list();
		List<Key<Object>> owners = new ArrayList<Key<Object>>();

		// TODO get OWNERS
		for (Book book : books)
			owners.add(Ref.create(book).getKey().getParent());
		return books;
	}
}
