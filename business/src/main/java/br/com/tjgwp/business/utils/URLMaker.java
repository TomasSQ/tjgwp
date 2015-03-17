package br.com.tjgwp.business.utils;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.user.UserEntity;

public class URLMaker {

	public static String urlForChapter(Chapter chapter) {
		return chapter.getBook().get().getUrl() + "/chapter/" + chapter.getId();
	}

	public static String urlForBook(Book book) {
		return "book/" + book.getOwner().get().getId() + "/" + book.getId();
	}

	public static String urlForUser(UserEntity userEntity) {
		return "user/" + userEntity.getId();
	}
}
