package br.com.tjgwp.business.entity.text;

import br.com.tjgwp.business.entity.SuperEntity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = true)
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

	@SuppressWarnings("unchecked")
	public Ref<KeyWord> getRef() {
		return Ref.create(this);
	}

	public void createUrl() {
	}
}
