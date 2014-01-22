import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.service.image.ImageService;


public class ImageRS {

	@GET
	@Path("/uploadUrl/{image}")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getUploadUrl(@PathParam("image") String image) {
		return Response.ok(new ImageService().getUploadUrl(image)).build();
	}

	@GET
	@Path("/image/{image}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM + ";charset=utf-8")
	public void getImageUrl(@PathParam("image") String image, @Context HttpServletResponse response) throws IOException {
		new ImageService().getImage(image, response);
	}
}
