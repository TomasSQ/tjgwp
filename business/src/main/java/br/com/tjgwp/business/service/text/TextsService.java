package br.com.tjgwp.business.service.text;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.BadRequestException;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.domain.text.TextsDomain;
import br.com.tjgwp.domain.user.UserDomain;

import com.googlecode.objectify.Ref;

public class TextsService extends SuperService {

	private TextsDomain textsDomain = new TextsDomain();

	public BookVO saveBook(BookVO newBook) {
		if (newBook == null)
			throw new BadRequestException();

		UserEntity loggedUser = new UserService().getLoggedUser(true);
		Book book = newBook.getId() != null ? mergeBook(newBook, loggedUser) : new Book(newBook);

		textsDomain.save(book);
		loggedUser.getBooks().add(Ref.create(book));
		
		new UserDomain().save(loggedUser);

		return new BookVO(book);

	}

	protected Book mergeBook(BookVO newBook, UserEntity loggedUser) {
		Book book = findBookFromUserById(loggedUser, newBook.getId());

		if (book == null)
			throw new BadRequestException();

		book.setSingleChapter(newBook.isSingleChapter());
		book.setSynopsis(newBook.getSynopsis());
		book.setTitle(newBook.getTitle());

		return book;
	}

	public ChapterVO saveChapter(Long bookId, ChapterVO newChapter) {
		if (newChapter == null || bookId == null)
			throw new BadRequestException();

		UserEntity loggedUser = new UserService().getLoggedUser(true);
		Book book = findBookFromUserById(loggedUser, bookId);

		if (book == null)
			throw new BadRequestException();

		Chapter chapter = newChapter.getId() != null ? mergeChapter(newChapter, book) : new Chapter(newChapter);

		textsDomain.save(chapter);
		book.getChapters().add(Ref.create(chapter));
		textsDomain.save(book);
		
		new UserDomain().save(loggedUser);

		return new ChapterVO(chapter);
	}

	protected Chapter mergeChapter(ChapterVO newChapter, Book book) {
		Chapter chapter = findChapterFromBookById(book, newChapter.getId());

		if (chapter == null)
			throw new BadRequestException();

		chapter.setTitle(newChapter.getTitle());
		chapter.setTextEntry(newChapter.getTextEntry());
		chapter.setPublishDate(newChapter.getPublishDate());

		return chapter;
	}

	protected Chapter findChapterFromBookById(Book book, Long chapterId) {
		for (Ref<Chapter> chapterRef : book.getChapters()) {
			Chapter chapter = chapterRef.get();
			if (chapter.getId().equals(chapterId))
				return chapter;
		}

		throw new BadRequestException();
	}

	protected Book findBookFromUserById(UserEntity user, Long bookId) {
		for (Ref<Book> bookRef : user.getBooks()) {
			Book book = bookRef.get();
			if (book.getId().equals(bookId))
				return bookRef.get();
		}

		throw new BadRequestException();
	}

}
