package br.com.tjgwp.business.entity.user;

import br.com.tjgwp.business.entity.Entity;

import com.google.appengine.api.users.User;

public class UserEntity extends Entity {

	private String email;
	private String nickname;

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

}
