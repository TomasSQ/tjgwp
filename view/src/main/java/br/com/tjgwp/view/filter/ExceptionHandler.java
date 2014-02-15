package br.com.tjgwp.view.filter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.tjgwp.business.service.UnauthorizedException;
import br.com.tjgwp.business.service.ValidationException;
import br.com.tjgwp.view.utils.GsonUtils;

import com.googlecode.objectify.NotFoundException;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		if (e instanceof ValidationException)
			return Response.status(Response.Status.BAD_REQUEST).entity(new GsonUtils().toJson(((ValidationException) e).getMessages())).build();

		if (e instanceof UnauthorizedException)
			return Response.status(Response.Status.FORBIDDEN).build();

		e.printStackTrace();
		if (e instanceof NotFoundException || e instanceof javax.ws.rs.NotFoundException)
			return Response.status(Response.Status.NOT_FOUND).build();

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}
