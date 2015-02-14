package br.com.tjgwp.business.entity.text;

import br.com.tjgwp.business.entity.SuperVO;

public class ChapterVO extends SuperVO {

	private Long bookId;
	private String title;
	private String textEntry;
	private String capeImageUrl;
	private boolean isPublished;
	private Long publishDate;

	public ChapterVO() {
	}

	public ChapterVO(Chapter chapter, Long bookId) {
		super.setId(chapter.getId());
		setTitle(chapter.getTitle());
		setTextEntry(chapter.getTextEntry() == null ? "" : chapter.getTextEntry());
		setCapeImageUrl(chapter.getCape().getUrl());
		setPublished(chapter.getPublishDate() != null);
		setPublishDate(chapter.getPublishDate());
		setBookId(bookId);
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTextEntry() {
		return textEntry;
	}

	protected void setTextEntry(String textEntry) {
		this.textEntry = textEntry;
	}

	public String getCapeImageUrl() {
		return capeImageUrl;
	}

	protected void setCapeImageUrl(String capeImageUrl) {
		this.capeImageUrl = capeImageUrl;
	}

	public boolean isPublished() {
		return isPublished;
	}

	protected void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Long getPublishDate() {
		return publishDate;
	}

	protected void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}

}
