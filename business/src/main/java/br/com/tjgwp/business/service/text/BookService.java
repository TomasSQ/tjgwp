package br.com.tjgwp.business.service.text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.entity.user.UserHistoryType;
import br.com.tjgwp.business.service.BadRequestException;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.business.service.ValidationException;
import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.domain.text.BookDomain;
import br.com.tjgwp.domain.user.UserDomain;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Work;

public class BookService extends SuperService {

	private UserService userSerivce = new UserService();
	private UserDomain userDomain = new UserDomain();
	private BookDomain bookDomain = new BookDomain();

	public WriteVO save(String bookId, String chapterId, WriteVO writeVO) throws ValidationException {
		if (writeVO == null)
			throw new BadRequestException();
		if ((!StringUtils.isNumeric(bookId) && writeVO.getBookId() == null && StringUtils.isBlank(writeVO.getBookTitle())) ||
				(!StringUtils.isNumeric(chapterId) && writeVO.getChapterId() == null && StringUtils.isBlank(writeVO.getChapterTitle())))
			throw new ValidationException("fill-the-titles");

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
		Book book = getBook(bookId);

		if (book.getId() == null)
			book.setTitle(writeVO.getBookTitle());
		return book;
	}

	public Book getBook(String bookId) {
		Book book = StringUtils.isNumeric(bookId) ? bookDomain.findById(Long.parseLong(bookId), Book.class) : new Book();

		if (book == null)
			throw new NotFoundException();
		return book;
	}

	public void saveBook(BookVO newBook) {
		if (newBook == null || newBook.getId() == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getLoggedUser(true);
		Book book = findBookFromUserById(loggedUser, newBook.getId());

		book.setSingleChapter(newBook.isSingleChapter());
		book.setSynopsis(newBook.getSynopsis());
		book.setTitle(newBook.getTitle());

		bookDomain.save(book);
	}

	public Book findBookFromUserById(UserEntity user, Long bookId) throws NotFoundException {
		for (Ref<Book> bookRef : user.getBooks()) {
			Book book = bookRef.get();
			if (book.getId().equals(bookId))
				return book;
		}

		throw new NotFoundException();
	}

	public void saveChapter(String bookId, ChapterVO newChapter) {
		if (newChapter == null || bookId == null || newChapter.getId() == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getLoggedUser(true);
		Book book = findBookFromUserById(loggedUser, StringUtils.isNumeric(bookId) ? Long.parseLong(bookId) : loggedUser.getId());

		Chapter chapter = findChapterFromBookById(book, newChapter.getId());

		chapter.setTitle(newChapter.getTitle());
		chapter.setTextEntry(newChapter.getTextEntry());

		bookDomain.save(chapter);
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

	public List<BookVO> getBooksFromUser(Long userId, boolean loadChapters) throws NotFoundException {
		UserEntity user = userSerivce.getUserOrMe(userId);

		List<BookVO> books = new ArrayList<BookVO>();
		for (Ref<Book> ref : user.getBooks()) {
			Book book = ref.get();
			if (book.getPublishDate() != null || userId == null)
				books.add(new BookVO(book, loadChapters));
		}

		return books;
	}

	public BookVO getFullBook(Long userId, Long bookId) throws NotFoundException {
		return new BookVO(findBookFromUserById(userSerivce.getUserOrMe(userId), bookId));
	}

	public ChapterVO getChapterFromBook(Long bookId, Long chapterId) throws NotFoundException {
		return new ChapterVO(findChapterFromBookById(findBookFromUserById(userSerivce.getLoggedUser(true), bookId), chapterId), bookId);
	}

	public WriteVO getWriteVO() {
		return new WriteVO(getBooksFromUser(null, true));
	}

	public void publishBook(Long userId, Long bookId) throws NotFoundException {
		UserEntity userEntity = userSerivce.getUserOrMe(userId);

		Book book = findBookFromUserById(userEntity, bookId);

		Date publishDate = Calendar.getInstance().getTime();
		book.setPublishDate(publishDate);
		for (Chapter chapter : bookDomain.get(book.getChapters())) {
			chapter.setPublishDate(publishDate);
			bookDomain.save(chapter);
		}

		bookDomain.save(book);

		userSerivce.createNewBookUserHistory(userEntity, Ref.create(book));
	}

	public Book saveBookCape(String id, HttpServletRequest req) {
		final BlobKey blobKey = new ImageService().getBlobFromRequest("bookCape", req);
		final String bookId = id;

		return ObjectifyService.run(new Work<Book>() {
			@Override
			public Book run() {
				UserEntity user = userSerivce.getLoggedUser(true);
				Book book = findBookFromUserById(user, StringUtils.isNumeric(bookId) ? Long.parseLong(bookId) : user.getId());

				if (blobKey != null) {
					book.updateCape(blobKey);
					userSerivce.createNewUserHistory(user, book.getCapeRef(), UserHistoryType.CHANGED_BOOK_CAPE);
					new UserDomain().save(book.getCape());
					new UserDomain().save(book);
				}

				return book;
			}
		});
	}

	public Chapter saveChapterCape(String id, Long idchapter, HttpServletRequest req) {
		final BlobKey blobKey = new ImageService().getBlobFromRequest("chapterCape", req);
		final String bookId = id;
		final Long chapterId = idchapter;

		return ObjectifyService.run(new Work<Chapter>() {
			@Override
			public Chapter run() {
				UserEntity user = userSerivce.getLoggedUser(true);
				Chapter chapter = findChapterFromBookById(findBookFromUserById(user, StringUtils.isNumeric(bookId) ? Long.parseLong(bookId) : user.getId()), chapterId);

				if (blobKey != null) {
					chapter.updateCape(blobKey);
					userSerivce.createNewUserHistory(user, chapter.getCapeRef(), UserHistoryType.CHANGED_BOOK_CAPE);
					new UserDomain().save(chapter.getCape());
					new UserDomain().save(chapter);
				}

				return chapter;
			}
		});
	}
}
