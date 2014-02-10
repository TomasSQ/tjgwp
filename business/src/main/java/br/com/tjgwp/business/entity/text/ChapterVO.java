package br.com.tjgwp.business.entity.text;

import java.util.Date;

import br.com.tjgwp.business.entity.SuperVO;

public class ChapterVO extends SuperVO {

	private String title;
	private String textEntry;
	private String capeImageUrl;
	private boolean isPublished;
	private Date publishDate;

	public ChapterVO(Chapter chapter) {
		super.setId(chapter.getId());
		setTitle(chapter.getTitle());
		setTextEntry(chapter.getTextEntry());
		setCapeImageUrl(chapter.getCape().getUrl());
		setPublished(chapter.getPublishDate() != null);
		setPublishDate(chapter.getPublishDate());
	}

	public String getTitle() {
		return title;
	}

	protected void setTitle(String title) {
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

	protected  void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	protected void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}
