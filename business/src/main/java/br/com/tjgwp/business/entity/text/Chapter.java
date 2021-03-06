package br.com.tjgwp.business.entity.text;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.business.utils.URLMaker;

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
	private Long publishDate;
	private Ref<Book> book;

	protected Chapter() {
		super();
	}

	public Chapter(Book book) {
		this.book = book.getRef();
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

	public Ref<Image> getCapeRef() {
		return cape;
	}

	public Image getCape() {
		return cape == null ? new Image() : cape.get();
	}

	public void updateCape(BlobKey cape) {
		if (this.cape == null)
			this.cape = new ImageService().newImageRef(this);

		this.cape.get().updateBlob(cape);
	}

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}

	public Ref<Book> getBook() {
		return book;
	}

	public void setBook(Ref<Book> book) {
		this.book = book;
	}

	public void setCape(Ref<Image> cape) {
		this.cape = cape;
	}

	@SuppressWarnings("unchecked")
	public Ref<Chapter> getRef() {
		return Ref.create(this);
	}

	public void createUrl() {
		setUrl(URLMaker.urlForChapter(this));
	}
}
