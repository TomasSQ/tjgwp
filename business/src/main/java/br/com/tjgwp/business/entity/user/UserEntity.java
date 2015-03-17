package br.com.tjgwp.business.entity.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.business.utils.URLMaker;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.gson.Gson;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = true)
public class UserEntity extends SuperEntity {

	@Index
	private String email;
	private String nickname;
	@Index
	private String nicknameDowncase;
	@Load
	private Ref<Image> profile;
	@Load
	private Ref<Image> background;

	private List<Ref<Book>> books;

	private List<Ref<UserEntity>> following;
	private List<Ref<UserEntity>> followers;

	public UserEntity() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		setNicknameDowncase(StringUtils.isNotEmpty(nickname) ? nickname.toLowerCase() : nickname); 
	}

	public String getNicknameDowncase() {
		return nicknameDowncase;
	}

	public void setNicknameDowncase(String nicknameDowncase) {
		this.nicknameDowncase = nicknameDowncase;
	}

	public Ref<Image> getProfileRef() {
		return profile;
	}

	public Image getProfile() {
		return profile == null ? new Image() : profile.get();
	}
	
	public void updateProfile(BlobKey profile) {
		if (this.profile == null)
			this.profile = new ImageService().newImageRef(this);

		this.profile.get().updateBlob(profile);
	}

	public Ref<Image> getBackgroundRef() {
		return background;
	}

	public Image getBackground() {
		return background == null ? new Image() : background.get();
	}

	public void updateBackground(BlobKey background) {
		if (this.background == null)
			this.background = new ImageService().newImageRef(this);

		this.background.get().updateBlob(background);
	}

	public List<Ref<Book>> getBooks() {
		if (books == null)
			books = new ArrayList<Ref<Book>>();

		return books;
	}

	public void setBooks(List<Ref<Book>> books) {
		this.books = books;
	}

	public List<Ref<UserEntity>> getFollowing() {
		if (following == null)
			following = new ArrayList<Ref<UserEntity>>();
		return following;
	}

	public void setFollowing(List<Ref<UserEntity>> following) {
		this.following = following;
	}

	public List<Ref<UserEntity>> getFollowers() {
		if (followers == null)
			followers = new ArrayList<Ref<UserEntity>>();
		return followers;
	}

	public void setFollowers(List<Ref<UserEntity>> followers) {
		this.followers = followers;
	}

	public String toJson() {
		int publishedBooks = 0;
		int publishedChapters = 0;

		for (Ref<Book> refBook : getBooks()) {
			Book book = refBook.get();
			if (book.getPublishDate() != null) {
				publishedBooks++;
				publishedChapters += book.publishedChaptersCount();
			}
		}

		return new Gson().toJson(new UserVO(this, publishedBooks, publishedChapters));
	}

	@SuppressWarnings("unchecked")
	public Ref<UserEntity> getRef() {
		return Ref.create(this);
	}

	public void createUrl() {
		setUrl(URLMaker.urlForUser(this));
	}
}
