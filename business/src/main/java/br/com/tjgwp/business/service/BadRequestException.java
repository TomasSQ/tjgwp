package br.com.tjgwp.business.service;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -1808405337769493526L;

	public BadRequestException() {
		super("bad-arguments");
	}
}
