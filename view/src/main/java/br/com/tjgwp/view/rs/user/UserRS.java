package br.com.tjgwp.view.rs.user;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.view.SuperRS;

@Path("/user")
public class UserRS extends SuperRS {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUser() {
		return Response.ok(new UserService().getLoggedUser().toJson()).build();
	}

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUser(@PathParam("userId") Long id) {
		return Response.ok(new UserService().getUser(id).toJson()).build();
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

	@GET
	@Path("/history")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getHistory() {
		return Response.ok(new UserService().getLastestHistoryFromUserAsJson(null)).build();
	}

	@GET
	@Path("/history/{userId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getHistoryFromUser(@PathParam("userId") Long id) {
		return Response.ok(new UserService().getLastestHistoryFromUserAsJson(id)).build();
	}

	@PUT
	@Path("/follow/{userId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response followUser(@PathParam("userId") Long id) {
		new UserService().followUser(id);
		return Response.ok().build();
	}

}
