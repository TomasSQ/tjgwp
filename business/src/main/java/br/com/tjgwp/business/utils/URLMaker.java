package br.com.tjgwp.business.utils;

import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.user.UserEntity;

public class URLMaker {

	public static String urlForChapter(UserEntity userEntity, SuperEntity book, SuperEntity chapter) {
		return urlForBook(userEntity, book) + "/chapter/" + chapter.getId();
	}

	public static String urlForBook(UserEntity userEntity, SuperEntity book) {
		return "book/fromUser/" + userEntity.getId() + "/" + book.getId();
	}

	public static String urlForUser(UserEntity userEntity) {
		return "user/" + userEntity.getId();
	}
}
