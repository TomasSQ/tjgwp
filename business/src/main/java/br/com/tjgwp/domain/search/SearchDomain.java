package br.com.tjgwp.domain.search;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.domain.SuperDomain;

public class SearchDomain extends SuperDomain {

	public List<UserEntity> searchUser(String word) {
		return ofy().load().type(UserEntity.class).filter("nicknameDowncase >=", word.toLowerCase()).filter("nicknameDowncase <", word.toLowerCase() + '\uFFFD').list();
	}

	public List<Book> searchBook(String word) {
		List<Book> books = ofy().load().type(Book.class).filter("titleDowncase >=", word.toLowerCase()).filter("titleDowncase <", word.toLowerCase() + '\uFFFD').list();
		List<Book> filteredBooks = new ArrayList<Book>();

		for (Book book : books)
			if (book.getPublishDate() != null)
				filteredBooks.add(book);

		return filteredBooks;
	}
}
