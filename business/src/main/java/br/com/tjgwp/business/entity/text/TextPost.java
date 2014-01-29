package br.com.tjgwp.business.entity.text;

import java.util.List;

import br.com.tjgwp.business.entity.SuperEntity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Index;

@EntitySubclass(index = true)
public class TextPost extends SuperEntity {

	@Index
	private String title;
	private String textEntry;
	private List<Ref<KeyWord>> keysWord;

	public String getTitle() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public List<Ref<KeyWord>> getKeysWord() {
		return keysWord;
	}

	public void setKeysWord(List<Ref<KeyWord>> keysWord) {
		this.keysWord = keysWord;
	}

	public String getTextEntry() {
		return textEntry;
	}

	public void setTextEntry(String textEntry) {
		this.textEntry = textEntry;
	}
}