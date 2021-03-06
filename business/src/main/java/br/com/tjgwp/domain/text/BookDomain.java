package br.com.tjgwp.domain.text;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.domain.SuperDomain;
import br.com.tjgwp.domain.user.UserDomain;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Ref;

public class BookDomain extends SuperDomain {

	public Book findByIdWithOwner(Long bookId, UserEntity owner) {
		Book book = findById(bookId, Book.class);
		if (book.getOwner().get().getId().equals(owner.getId()))
			return book;

		throw new NotFoundException();
	}

	public Chapter findChapterOfBook(Long chapterId, Long bookId) {
		Chapter chapter = findById(chapterId, Chapter.class);
		if (chapter.getBook().get().getId().equals(bookId))
			return chapter;

		throw new NotFoundException();
	}

	public void remove(UserEntity userEntity, Book book, Chapter chapter, int i) {
		remove(userEntity, book, chapter, i, true);
	}

	private void remove(UserEntity userEntity, Book book, Chapter chapter, int i, boolean saveBook) {
		book.getChapters().remove(i);

		if (saveBook)
			save(book);
		new UserDomain().removeChapterUserHistory(userEntity, book, chapter);

		ofy().delete().keys(ofy().load().ancestor(chapter).keys().list()).now();
	}

	public void remove(UserEntity userEntity, Book book,  int i) {
		userEntity.getBooks().remove(i);
		save(userEntity);

		for (int j = 0; j < book.getChapters().size(); j++) {
			Ref<Chapter> chapterRef = book.getChapters().get(j);
			remove(userEntity, book, chapterRef.get(), j++, false);
		}

		new UserDomain().removeBookUserHistory(userEntity, book);
		ofy().delete().keys(ofy().load().ancestor(book).keys().list()).now();
	}


}
