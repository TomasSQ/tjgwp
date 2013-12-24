package br.com.tjgwp.view.rs.user;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.view.utils.GsonUtils;

@Path("/user")
public class UserRS {

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUser() {
		return Response.ok(GsonUtils.toJson(new UserService().getLoggedUser())).build();
	}

	@GET
	@Path("/loginUrl")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getLoginUrl(@Context HttpServletRequest req) {
		return Response.ok(GsonUtils.toJson(new UserService().getLoginUrl(req))).build();
	}
}
