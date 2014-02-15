package br.com.tjgwp.business.service;

import java.util.Arrays;
import java.util.List;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 6399492196132659987L;
	private List<String> messages;

	public ValidationException(String message) {
		super(message);

		messages = Arrays.asList(message);
	}

	public ValidationException(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

}
