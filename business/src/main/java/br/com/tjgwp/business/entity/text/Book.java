package br.com.tjgwp.business.entity.text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Load;

@EntitySubclass(index = true)
public class Book extends SuperEntity {

	private String title;
	private String synopsis;
	private List<Ref<Chapter>> chapters;
	private boolean isSingleChapter;
	private List<Ref<KeyWord>> keysWord;
	private Date publishDate;
	@Load
	private Ref<Image> cape;

	public Book() {
		super();
	}

	public Book(BookVO book) {
		this();
		super.setId(book.getId());
		setTitle(book.getTitle());
		setSynopsis(book.getSynopsis());
		setSingleChapter(book.isSingleChapter());
		if (book.isPublished())
			setPublishDate(book.getPublishDate());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public List<Ref<Chapter>> getChapters() {
		if (chapters == null)
			chapters = new ArrayList<Ref<Chapter>>();

		return chapters;
	}

	public void setChapters(List<Ref<Chapter>> chapters) {
		this.chapters = chapters;
	}

	public boolean isSingleChapter() {
		return isSingleChapter;
	}

	public void setSingleChapter(boolean isSingleChapter) {
		this.isSingleChapter = isSingleChapter;
	}

	public List<Ref<KeyWord>> getKeysWord() {
		if (keysWord == null)
			keysWord = new ArrayList<Ref<KeyWord>>();

		return keysWord;
	}

	public void setKeysWord(List<Ref<KeyWord>> keysWord) {
		this.keysWord = keysWord;
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
