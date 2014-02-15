package br.com.tjgwp.business.entity.text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tjgwp.business.entity.SuperVO;
import br.com.tjgwp.domain.SuperDomain;

import com.googlecode.objectify.Ref;

public class BookVO extends SuperVO {

	private String title;
	private String synopsis;
	private List<ChapterVO> chapters;
	private boolean isSingleChapter;
	private List<KeyWord> keysWord;
	private boolean isPublished;
	private Date publishDate;
	private String capeImageUrl;

	public BookVO(Book book) {
		this(book, true);
	}

	public BookVO(Book book, boolean loadChapter) {
		super.setId(book.getId());
		setTitle(book.getTitle());
		setSynopsis(book.getSynopsis());

		if (loadChapter)
			setChapters(book.getChapters());

		setSingleChapter(book.isSingleChapter());
		setKeysWord(book.getKeysWord());
		setPublished(book.getPublishDate() != null);
		setPublishDate(book.getPublishDate());
		setCapeImageUrl(book.getCape().getUrl());
	}

	public String getTitle() {
		return title;
	}

	protected void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	protected void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public List<ChapterVO> getChapters() {
		return chapters;
	}

	protected void setChapters(List<Ref<Chapter>> chaptersRef) {
		List<Chapter> chapters = new SuperDomain().get(chaptersRef);

		this.chapters = new ArrayList<ChapterVO>();
		for (Chapter chapter : chapters)
			this.chapters.add(new ChapterVO(chapter, getId()));
	}

	public boolean isSingleChapter() {
		return isSingleChapter;
	}

	protected void setSingleChapter(boolean isSingleChapter) {
		this.isSingleChapter = isSingleChapter;
	}

	public List<KeyWord> getKeysWord() {
		return keysWord;
	}

	protected void setKeysWord(List<Ref<KeyWord>> keysWord) {
		this.keysWord = new SuperDomain().get(keysWord);
	}

	public boolean isPublished() {
		return isPublished;
	}

	protected void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	protected void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getCapeImageUrl() {
		return capeImageUrl;
	}

	protected void setCapeImageUrl(String capeImageUrl) {
		this.capeImageUrl = capeImageUrl;
	}

}
