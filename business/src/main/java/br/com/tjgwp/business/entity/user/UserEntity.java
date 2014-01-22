package br.com.tjgwp.business.entity.user;

import br.com.tjgwp.business.entity.SuperEntity;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Index;

@EntitySubclass(index=true)
public class UserEntity extends SuperEntity {

	@Index
	private String email;
	private String nickname;
	private BlobKey profile;
	private String profileImageUrl;
	private BlobKey background;

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
	
	public void setProfile(BlobKey profile) {
		this.profile = profile;

		ImagesService imageService = ImagesServiceFactory.getImagesService();
        setProfileImageUrl(imageService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(profile)));
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

	public void setBackground(BlobKey background) {
		this.background = background;
	}

}
