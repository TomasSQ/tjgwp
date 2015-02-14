package br.com.tjgwp.business.entity.user;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.domain.SuperDomain;

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
	@Index
	private String nickname;
	@Load
	private Ref<Image> profile;
	@Load
	private Ref<Image> background;

	private List<Ref<Book>> books;

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
	}

	public Ref<Image> getProfileRef() {
		return profile;
	}

	public Image getProfile() {
		return profile == null ? new Image() : profile.get();
	}
	
	@SuppressWarnings("unchecked")
	public void updateProfile(BlobKey profile) {
		if (this.profile == null)
			this.profile = (Ref<Image>) Ref.create(new SuperDomain().save(new Image()));

		this.profile.get().updateBlob(profile);
	}

	public Ref<Image> getBackgroundRef() {
		return background;
	}

	public Image getBackground() {
		return background == null ? new Image() : background.get();
	}

	@SuppressWarnings("unchecked")
	public void updateBackground(BlobKey background) {
		if (this.background == null)
			this.background = (Ref<Image>) Ref.create(new SuperDomain().save(new Image()));

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

}
