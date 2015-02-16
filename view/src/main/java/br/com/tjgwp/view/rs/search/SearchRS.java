package br.com.tjgwp.view.rs.search;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.service.search.SearchService;
import br.com.tjgwp.view.SuperRS;

@Path("/search")
public class SearchRS extends SuperRS {

	@GET
	@Path("/{word}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response searchWord(@PathParam("word") String word) {
		return Response.ok(gsonUtils.toJson(new SearchService().search(word))).build();
	}

	
}
