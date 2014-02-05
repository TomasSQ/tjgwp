package br.com.tjgwp.business.service;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException() {
		super("unauthorized");
	}

	private static final long serialVersionUID = 6399492196132659987L;

}
