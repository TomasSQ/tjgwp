package br.com.tjgwp.business.service.text;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.ChapterVO;

public class WriteVO {

	private List<BookVO> books;
	private List<ChapterVO> chapters;

	public WriteVO(List<BookVO> books) {
		setBooks(books);

		chapters = new ArrayList<ChapterVO>();
		for (BookVO book : books)
			chapters.addAll(book.getChapters());
	}

	public List<BookVO> getBooks() {
		return books;
	}

	public void setBooks(List<BookVO> books) {
		this.books = books;
	}

	public List<ChapterVO> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterVO> chapters) {
		this.chapters = chapters;
	}

}
