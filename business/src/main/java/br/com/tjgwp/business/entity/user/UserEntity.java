package br.com.tjgwp.business.entity.user;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.TextPost;
import br.com.tjgwp.business.service.image.ImageService;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Index;

@EntitySubclass(index = true)
public class UserEntity extends SuperEntity {

	@Index
	private String email;
	@Index
	private String nickname;
	private BlobKey profile;
	private String profileImageUrl;
	private BlobKey background;
	private String backgroundImageUrl;

	private List<Ref<TextPost>> postedTexts;

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

	public BlobKey getProfile() {
		return profile;
	}
	
	public void updateProfile(BlobKey profile) {
		ImageService imageService = new ImageService();
		if (this.profile != null)
			imageService.deleteBlob(this.profile);

		if (profile != null) {
			this.profile = profile;
	
			setProfileImageUrl(imageService.getUrlForBlob(profile));
		}	
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public BlobKey getBackground() {
		return background;
	}

	public void updateBackground(BlobKey background) {
		ImageService imageService = new ImageService();
		if (this.background != null)
			imageService.deleteBlob(this.background);

		if (background != null) {
			this.background = background;
	
			setBackgroundImageUrl(imageService.getUrlForBlob(background));
		}
	}

	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public List<Ref<TextPost>> getPostedTexts() {
		if (postedTexts == null)
			postedTexts = new ArrayList<Ref<TextPost>>();
		return postedTexts;
	}

	public void setPostedTexts(List<Ref<TextPost>> postedTexts) {
		this.postedTexts = postedTexts;
	}

	public String toJson() {
		return new Gson().toJson(new UserVO(this));
	}
}
