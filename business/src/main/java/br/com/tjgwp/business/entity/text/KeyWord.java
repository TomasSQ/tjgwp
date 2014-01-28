package br.com.tjgwp.business.entity.text;

import br.com.tjgwp.business.entity.SuperEntity;

import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Index;

@EntitySubclass(index = true)
public class KeyWord extends SuperEntity {

	@Index
	private String name;
	@Index
	private Gender gender;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
