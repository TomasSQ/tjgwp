package br.com.tjgwp.business.service.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.ChapterVO;

public class WriteVO {

	private Map<Long, String> books = new HashMap<Long, String>();
	private Map<Long, ChapterVO> chapters = new HashMap<Long, ChapterVO>();
	private String bookTitle;
	private String chapterTitle;
	private String textEntry;

	public WriteVO(List<BookVO> booksVO) {
		for (BookVO book : booksVO) {
			books.put(book.getId(), book.getTitle());

			for (ChapterVO chapterVO : book.getChapters()) {
				ChapterVO chapter = new ChapterVO();
				chapter.setId(chapterVO.getId());
				chapter.setBookId(book.getId());
				chapter.setTitle(chapterVO.getTitle());
				chapters.put(chapterVO.getId(), chapter);
			}
		}
	}

	public Map<Long, String> getBooks() {
		return books;
	}

	public Map<Long, ChapterVO> getChapters() {
		return chapters;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public String getTextEntry() {
		return textEntry;
	}

	public void setTextEntry(String textEntry) {
		this.textEntry = textEntry;
	}

	public void setBooks(Map<Long, String> books) {
		this.books = books;
	}

	public void setChapters(Map<Long, ChapterVO> chapters) {
		this.chapters = chapters;
	}

}
