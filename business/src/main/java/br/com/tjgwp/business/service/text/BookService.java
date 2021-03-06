package br.com.tjgwp.business.service.text;

import java.util.ArrayList;
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

		UserEntity me = userSerivce.getMe();
		Book book = getMyBook(bookId, writeVO, me);
		Chapter chapter = getMyChapter(chapterId, writeVO, book);
		bookDomain.save(chapter);

		boolean isNewChapter = !StringUtils.isNumeric(chapterId);
		boolean isNewBook = !StringUtils.isNumeric(bookId);
		if (isNewChapter)
			book.getChapters().add(chapter.getRef());
		bookDomain.save(book);
		if (isNewBook)
			me.getBooks().add(book.getRef());
		userDomain.save(me);

		writeVO = new BookService().getWriteVO();
		writeVO.setBookId(book.getId());
		writeVO.setChapterId(chapter.getId());
		writeVO.setTextEntry(chapter.getTextEntry());

		return writeVO;
	}

	protected Chapter getMyChapter(String chapterId, WriteVO writeVO, Book book) {
		Chapter chapter = StringUtils.isNumeric(chapterId) ? bookDomain.findChapterOfBook(Long.parseLong(chapterId), book.getId()) : new Chapter(book);

		if (chapter == null)
			throw new NotFoundException();

		if (chapter.getId() == null)
			chapter.setTitle(writeVO.getChapterTitle());

		chapter.setTextEntry(writeVO.getTextEntry());

		return chapter;
	}

	protected Book getMyBook(String bookId, WriteVO writeVO, UserEntity me) {
		Book book = StringUtils.isNumeric(bookId) ? bookDomain.findById(Long.parseLong(bookId), Book.class) : new Book(me);

		if (book.getId() == null) {
			book.setTitle(writeVO.getBookTitle());
			bookDomain.save(book);
		}
		return book;
	}

	public Book getBook(String bookId) {
		Book book = bookDomain.findById(Long.parseLong(bookId), Book.class);

		if (book == null)
			throw new NotFoundException();
		return book;
	}

	public void saveBook(BookVO newBook) {
		if (newBook == null || newBook.getId() == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getMe();
		Book book = findBookFromUserById(loggedUser, newBook.getId(), false);

		book.setSingleChapter(newBook.isSingleChapter());
		book.setSynopsis(newBook.getSynopsis());
		book.setTitle(newBook.getTitle());

		bookDomain.save(book);
	}

	public Book findBookFromUserById(UserEntity user, Long bookId, boolean validatePublishDate) throws NotFoundException {
		if (bookId == null)
			throw new NotFoundException();

		for (Ref<Book> bookRef : user.getBooks()) {
			Book book = bookRef.get();
			if (book.getId().equals(bookId) && (!validatePublishDate || book.getPublishDate() != null))
				return book;
		}

		throw new NotFoundException();
	}

	public void saveChapter(String bookId, ChapterVO newChapter) {
		if (newChapter == null || bookId == null || newChapter.getId() == null)
			throw new BadRequestException();

		UserEntity loggedUser = userSerivce.getMe();
		Book book = findBookFromUserById(loggedUser, StringUtils.isNumeric(bookId) ? Long.parseLong(bookId) : null, false);

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

	public List<BookVO> getMyBooks() throws NotFoundException {
		return getBooksFromUser(null);
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
		return new BookVO(findBookFromUserById(userSerivce.getUserOrMe(userId), bookId, userId != null));
	}

	public ChapterVO getChapterFromBook(Long bookId, Long chapterId) throws NotFoundException {
		return new ChapterVO(findChapterFromBookById(findBookFromUserById(userSerivce.getMe(), bookId, false), chapterId), bookId);
	}

	public WriteVO getWriteVO() {
		return new WriteVO(getBooksFromUser(null, true));
	}

	public void updateBookTitle(Long bookId, String title) throws NotFoundException {
		Book book = findBookFromUserById(userSerivce.getMe(), bookId, false);
		book.setTitle(title);
		bookDomain.save(book);
	}

	public void publishBook(Long bookId) throws NotFoundException {
		UserEntity userEntity = userSerivce.getMe();

		Book book = findBookFromUserById(userEntity, bookId, false);

		Long publishDate = System.currentTimeMillis();
		book.setPublishDate(publishDate);
		for (Chapter chapter : bookDomain.get(book.getChapters())) {
			chapter.setPublishDate(publishDate);
			bookDomain.save(chapter);
		}

		bookDomain.save(book);

		userSerivce.createNewBookUserHistory(userEntity, book.getRef());
	}

	public void deleteMyBook(Long bookId) throws NotFoundException {
		UserEntity userEntity = userSerivce.getMe();

		for (int i = 0; i < userEntity.getBooks().size(); i++) {
			Ref<Book> bookRef = userEntity.getBooks().get(i);
			Book book = bookRef.get();
			if (book.getId().equals(bookId)) {
				bookDomain.remove(userEntity, book, i);
				return;
			}
		}

	}

	public void deleteChapterFromBook(Long bookId, Long chapterId) throws NotFoundException {
		UserEntity userEntity = userSerivce.getMe();

		Book book = findBookFromUserById(userEntity, bookId, false);
		for (int i = 0; i < book.getChapters().size(); i++) {
			Ref<Chapter> chapterRef = book.getChapters().get(i);
			Chapter chapter = chapterRef.get();
			if (chapter.getId().equals(chapterId)) {
				bookDomain.remove(userEntity, book, chapter, i);
				return;
			}
		}
	}


	public Book saveBookCape(Long id, HttpServletRequest req) {
		final BlobKey blobKey = new ImageService().getBlobFromRequest("bookCape", req);
		final Long bookId = id;

		return ObjectifyService.run(new Work<Book>() {
			@Override
			public Book run() {
				UserEntity user = userSerivce.getMe();
				Book book = findBookFromUserById(user, bookId, false);

				if (blobKey != null) {
					book.updateCape(blobKey);
					if (book.getPublishDate() != null)
						userSerivce.createUserHistory(user, UserHistoryType.CHANGED_BOOK_CAPE, book.getRef());
					new UserDomain().save(book.getCape());
					new UserDomain().save(book);
				}

				return book;
			}
		});
	}

	public Chapter saveChapterCape(Long id, Long idchapter, HttpServletRequest req) {
		final BlobKey blobKey = new ImageService().getBlobFromRequest("chapterCape", req);
		final Long bookId = id;
		final Long chapterId = idchapter;

		return ObjectifyService.run(new Work<Chapter>() {
			@Override
			public Chapter run() {
				UserEntity user = userSerivce.getMe();
				Chapter chapter = findChapterFromBookById(findBookFromUserById(user, bookId, false), chapterId);

				if (blobKey != null) {
					chapter.updateCape(blobKey);
					if (chapter.getPublishDate() != null)
						userSerivce.createUserHistory(user, UserHistoryType.CHANGED_BOOK_CAPE, chapter.getRef());
					new UserDomain().save(chapter.getCape());
					new UserDomain().save(chapter);
				}

				return chapter;
			}
		});
	}
}
