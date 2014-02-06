package br.com.tjgwp.view.filter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.tjgwp.business.service.UnauthorizedException;

import com.googlecode.objectify.NotFoundException;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		e.printStackTrace();

		if (e instanceof UnauthorizedException)
			return Response.status(Response.Status.FORBIDDEN).build();
		if (e instanceof NotFoundException)
			return Response.status(Response.Status.NOT_FOUND).build();

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}
