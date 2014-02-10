package br.com.tjgwp.business.entity.user;

import br.com.tjgwp.business.entity.SuperVO;

public class UserVO extends SuperVO {

	private String email;
	private String nickname;
	private String profileImageUrl;
	private String backgroundImageUrl;
	private Integer postedTexts;

	public UserVO(UserEntity userEntity) {
		setId(userEntity.getId());
		setEmail(userEntity.getEmail());
		setNickname(userEntity.getNickname());
		setProfileImageUrl(userEntity.getProfile().getUrl());
		setBackgroundImageUrl(userEntity.getBackground().getUrl());
		setPostedTexts(userEntity.getBooks().size());
	}

	public String getEmail() {
		return email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	protected void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	protected void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	protected void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public Integer getPostedTexts() {
		return postedTexts;
	}

	protected void setPostedTexts(Integer postedTexts) {
		this.postedTexts = postedTexts;
	}

}
