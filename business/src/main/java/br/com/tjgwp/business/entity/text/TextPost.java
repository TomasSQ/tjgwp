package br.com.tjgwp.business.entity.text;

import java.util.List;

import br.com.tjgwp.business.entity.SuperEntity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass(index = true)
public class TextPost extends SuperEntity {

	private List<Ref<KeyWord>> keysWord;
	private String textEntry;

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
