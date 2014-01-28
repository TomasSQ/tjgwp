package br.com.tjgwp.business.entity.user;

public class UserVO {

	private Long id;
	private String email;
	private String nickname;
	private String profileImageUrl;
	private String backgroundImageUrl;

	public UserVO(UserEntity userEntity) {
		setId(userEntity.getId());
		setEmail(userEntity.getEmail());
		setNickname(userEntity.getNickname());
		setProfileImageUrl(userEntity.getProfileImageUrl());
		setBackgroundImageUrl(userEntity.getBackgroundImageUrl());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

}
