package br.com.tjgwp.business.entity.user;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.Book;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@EntitySubclass(index = true)
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

	protected UserEntity() {
	}

	public UserEntity(User user) {
		email = user == null ? "" : user.getEmail();
		nickname = user == null ? "" : user.getNickname();
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

	public Image getProfile() {
		return profile == null ? new Image() : profile.get();
	}
	
	public void updateProfile(BlobKey profile) {
		if (this.profile == null)
			this.profile = Ref.create(new Image());

		this.profile.get().updateBlob(profile);
	}

	public Image getBackground() {
		return background == null ? new Image() : background.get();
	}

	public void updateBackground(BlobKey background) {
		if (this.background == null)
			this.background = Ref.create(new Image());

		this.profile.get().updateBlob(background);
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
		return new Gson().toJson(new UserVO(this));
	}
}
