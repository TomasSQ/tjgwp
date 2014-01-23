package br.com.tjgwp.view.rs.image;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.view.utils.GsonUtils;

@Path("/image")
public class ImageRS {

	@GET
	@Path("/uploadUrl")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getUploadUrl(@QueryParam("callbackUrl") String callbackUrl) {
		return Response.ok(new ImageService().getUploadUrl(callbackUrl)).build();
	}

	@GET
	@Path("/uploadUrls")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUploadUrls(@QueryParam("callbacksUrl[]") List<String> callbacksUrl) {
		return Response.ok(GsonUtils.toJson(new ImageService().getUploadUrls(callbacksUrl))).build();
	}

}
