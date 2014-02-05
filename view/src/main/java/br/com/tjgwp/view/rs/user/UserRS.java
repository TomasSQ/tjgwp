package br.com.tjgwp.view.rs.user;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.view.SuperRS;

@Path("/user")
public class UserRS extends SuperRS {

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUser() {
		return Response.ok(new UserService().getLoggedUser().toJson()).build();
	}

	@POST
	@Path("/profilePic")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveProfilePic(@Context HttpServletRequest req) {
		return Response.ok(new UserService().saveProfilePic(req).toJson()).build();
	}

	@POST
	@Path("/backgroundPic")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveBackgroundPic(@Context HttpServletRequest req) {
		return Response.ok(new UserService().saveBackgroundPic(req).toJson()).build();
	}


	@GET
	@Path("/loginUrl")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getLoginUrl(@Context HttpServletRequest req) {
		return Response.ok(new UserService().getLoginUrl(req)).build();
	}

	@GET
	@Path("/logoutUrl")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getLogoutUrl(@Context HttpServletRequest req) {
		return Response.ok(new UserService().getLogoutUrl(req)).build();
	}
	
}
