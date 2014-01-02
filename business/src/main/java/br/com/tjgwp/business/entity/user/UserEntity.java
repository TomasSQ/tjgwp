package br.com.tjgwp.business.entity.user;

import br.com.tjgwp.business.entity.SuperEntity;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Index;

@EntitySubclass(index=true)
public class UserEntity extends SuperEntity {

	@Index
	private String email;
	private String nickname;

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

}
