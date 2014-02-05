package br.com.tjgwp.view.text;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.entity.text.TextPost;
import br.com.tjgwp.business.service.text.TextPostService;
import br.com.tjgwp.view.SuperRS;

@Path("/textPost")
public class TextPostRS extends SuperRS {

	@Path("/")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewText(@FormParam("textPost") String textPost) {
		return Response.ok(gsonUtils.toJson(new TextPostService().save(gsonUtils.fromJson(textPost, TextPost.class)))).build();
	}

}
