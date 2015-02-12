package br.com.tjgwp.business.entity.text;

import java.util.Date;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = true)
public class Chapter extends SuperEntity {

	@Index
	private String title;
	private String textEntry;
	@Load
	private Ref<Image> cape;
	private Date publishDate;

	public Chapter() {
		super();
	}

	public Chapter(ChapterVO chapter) {
		this();

		super.setId(chapter.getId());
		setTitle(chapter.getTitle());
		setTextEntry(chapter.getTextEntry());
		if (chapter.isPublished())
			setPublishDate(chapter.getPublishDate());
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

	public void setTextEntry(String textEntry) {
		this.textEntry = textEntry;
	}

	public Image getCape() {
		return cape == null ? new Image() : cape.get();
	}

	public void updateCape(BlobKey cape) {
		if (this.cape == null)
			this.cape = Ref.create(new Image());

		this.cape.get().updateBlob(cape);
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}
