package br.com.tjgwp.view.text;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.service.text.TextsService;
import br.com.tjgwp.view.SuperRS;

@Path("/texts")
public class TextPostRS extends SuperRS {

	@Path("/book")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewBook(@FormParam("book") String book) {
		return Response.ok(gsonUtils.toJson(new TextsService().saveBook(gsonUtils.fromJson(book, BookVO.class)))).build();
	}

	@Path("/{bookId}/chapter")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewText(@PathParam("bookId") Long bookId, @FormParam("chapter") String chapter) {
		return Response.ok(gsonUtils.toJson(new TextsService().saveChapter(bookId, gsonUtils.fromJson(chapter, ChapterVO.class)))).build();
	}

}
