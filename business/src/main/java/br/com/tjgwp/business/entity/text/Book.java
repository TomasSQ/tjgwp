package br.com.tjgwp.business.entity.text;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.business.utils.URLMaker;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = true)
public class Book extends SuperEntity {

	private String title;
	@Index
	private String titleDowncase;
	private String synopsis;
	private List<Ref<Chapter>> chapters;
	private boolean isSingleChapter;
	private List<Ref<KeyWord>> keysWord;
	private Long publishDate;
	@Load
	private Ref<Image> cape;
	private Ref<UserEntity> owner;

	protected Book() {
		super();
	}

	public Book(UserEntity userEntity) {
		this.owner = userEntity.getRef();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		setTitleDowncase(StringUtils.isNotEmpty(title) ? title.toLowerCase() : title);
	}

	public String getTitleDowncase() {
		return titleDowncase;
	}

	public void setTitleDowncase(String titleDowncase) {
		this.titleDowncase = titleDowncase;
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

	public int publishedChaptersCount() {
		int publishedChapters = 0;

		for (Ref<Chapter> refChapter : getChapters()) {
			Chapter chapter = refChapter.get();
			if (chapter.getPublishDate() != null)
				publishedChapters++;
		}

		return publishedChapters;
	}

	public Ref<UserEntity> getOwner() {
		return owner;
	}

	public void setOwner(Ref<UserEntity> owner) {
		this.owner = owner;
	}

	@SuppressWarnings("unchecked")
	public Ref<Book> getRef() {
		return Ref.create(this);
	}

	public void createUrl() {
		setUrl(URLMaker.urlForBook(this));
	}
}
