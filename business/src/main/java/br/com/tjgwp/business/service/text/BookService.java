package br.com.tjgwp.business.service.text;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.BadRequestException;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.domain.text.BookDomain;
import br.com.tjgwp.domain.user.UserDomain;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Ref;

public class BookService extends SuperService {

	private UserDomain userDomain = new UserDomain();
	private UserService userSerivce = new UserService();
	private BookDomain bookDomain = new BookDomain();

	public WriteVO save(String bookId, String chapterId, WriteVO writeVO) {
		if (StringUtils.isBlank(bookId) || StringUtils.isBlank(chapterId) || writeVO == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getLoggedUser(true);
		Book book = getBook(bookId, writeVO);
		Chapter chapter = getChapter(chapterId, writeVO);

		boolean isNewChapter = chapter.getId() == null;
		boolean isNewBook = book.getId() == null;
		bookDomain.save(chapter);
		if (isNewChapter)
			book.getChapters().add(Ref.create(chapter));
		bookDomain.save(book);
		if (isNewBook)
			loggedUser.getBooks().add(Ref.create(book));
		userDomain.save(loggedUser);

		writeVO = new BookService().getWriteVO();
		writeVO.setBookId(book.getId());
		writeVO.setChapterId(chapter.getId());
		writeVO.setTextEntry(chapter.getTextEntry());

		return writeVO;
	}

	protected Chapter getChapter(String chapterId, WriteVO writeVO) {
		Chapter chapter = StringUtils.isNumeric(chapterId) ? bookDomain.findById(Long.parseLong(chapterId), Chapter.class) : new Chapter();

		if (chapter == null)
			throw new NotFoundException();

		if (chapter.getId() == null)
			chapter.setTitle(writeVO.getChapterTitle());

		chapter.setTextEntry(writeVO.getTextEntry());

		return chapter;
	}

	protected Book getBook(String bookId, WriteVO writeVO) {
		Book book = StringUtils.isNumeric(bookId) ? bookDomain.findById(Long.parseLong(bookId), Book.class) : new Book();

		if (book == null)
			throw new NotFoundException();

		if (book.getId() == null)
			book.setTitle(writeVO.getBookTitle());
		return book;
	}

	public void saveBook(BookVO newBook) {
		if (newBook == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getLoggedUser(true);
		Book book = newBook.getId() != null ? mergeBook(newBook, loggedUser) : new Book(newBook);

		bookDomain.save(book);
		if (newBook.getId() == null) {
			loggedUser.getBooks().add(Ref.create(book));
			userDomain.save(loggedUser);
		}
	}

	protected Book mergeBook(BookVO newBook, UserEntity loggedUser) throws NotFoundException {
		Book book = findBookFromUserById(loggedUser, newBook.getId());

		book.setSingleChapter(newBook.isSingleChapter());
		book.setSynopsis(newBook.getSynopsis());
		book.setTitle(newBook.getTitle());

		return book;
	}

	protected Book findBookFromUserById(UserEntity user, Long bookId) throws NotFoundException {
		for (Ref<Book> bookRef : user.getBooks()) {
			Book book = bookRef.get();
			if (book.getId().equals(bookId))
				return bookRef.get();
		}

		throw new NotFoundException();
	}

	public void saveChapter(String bookId, ChapterVO newChapter) {
		if (newChapter == null || bookId == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getLoggedUser(true);
		Book book = findBookFromUserById(loggedUser, StringUtils.isNumeric(bookId) ? Long.parseLong(bookId) : loggedUser.getId());

		Chapter chapter = newChapter.getId() != null ? mergeChapter(newChapter, book) : new Chapter(newChapter);

		bookDomain.save(chapter);
		if (newChapter.getId() == null) {
			book.getChapters().add(Ref.create(chapter));
			bookDomain.save(book);
		}
	}

	protected Chapter mergeChapter(ChapterVO newChapter, Book book) {
		Chapter chapter = findChapterFromBookById(book, newChapter.getId());

		chapter.setTitle(newChapter.getTitle());
		chapter.setTextEntry(newChapter.getTextEntry());
		chapter.setPublishDate(newChapter.getPublishDate());

		return chapter;
	}

	protected Chapter findChapterFromBookById(Book book, Long chapterId) throws NotFoundException {
		for (Ref<Chapter> chapterRef : book.getChapters()) {
			Chapter chapter = chapterRef.get();
			if (chapter.getId().equals(chapterId))
				return chapter;
		}

		throw new NotFoundException();
	}

	public List<BookVO> getBooksFromUser(Long id) throws NotFoundException {
		return getBooksFromUser(id, false);
	}

	public List<BookVO> getBooksFromUser(Long id, boolean loadChapters) throws NotFoundException {
		UserEntity user = id == null ? new UserService().getLoggedUser(true) : userDomain.findById(id, UserEntity.class);

		List<BookVO> books = new ArrayList<BookVO>();
		for (Ref<Book> ref : user.getBooks())
			books.add(new BookVO(ref.get(), loadChapters));

		return books;
	}

	public BookVO getFullBook(Long userId, Long bookId) throws NotFoundException {
		return new BookVO(findBookFromUserById(userDomain.findById(userId, UserEntity.class), bookId));
	}

	public ChapterVO getChapterFromBook(Long bookId, Long chapterId) throws NotFoundException {
		return new ChapterVO(findChapterFromBookById(findBookFromUserById(userSerivce.getLoggedUser(true), bookId), chapterId), bookId);
	}

	public WriteVO getWriteVO() {
		return new WriteVO(getBooksFromUser(null, true));
	}

}
