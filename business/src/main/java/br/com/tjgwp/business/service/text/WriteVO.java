package br.com.tjgwp.business.service.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.ChapterVO;

public class WriteVO {

	private Map<Long, String> books = new HashMap<Long, String>();
	private Map<Long, ChapterVO> chapters = new HashMap<Long, ChapterVO>();

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

}
