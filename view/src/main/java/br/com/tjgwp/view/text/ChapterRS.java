package br.com.tjgwp.view.text;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.service.text.BookService;
import br.com.tjgwp.business.service.text.WriteVO;
import br.com.tjgwp.view.SuperRS;

@Path("/book/{bookId}/chapter")
public class ChapterRS extends SuperRS {

	@Path("/{chapterId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getChapterFromBook(@PathParam("bookId") Long book, @PathParam("chapterId") Long chapter, @FormParam("write") String write) {
		return Response.ok(gsonUtils.toJson(new BookService().getChapterFromBook(book, chapter))).build();
	}

	@Path("/")
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewChapter(@PathParam("bookId") String bookId, @FormParam("chapter") String chapter) {
		new BookService().saveChapter(bookId, gsonUtils.fromJson(chapter, ChapterVO.class));

		return Response.ok().build();
	}

	@Path("/{chapter}")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewBook(@PathParam("bookId") String bookId, @PathParam("chapter") String chapter, @FormParam("write") String write) {
		return Response.ok(gsonUtils.toJson(new BookService().save(bookId, chapter, gsonUtils.fromJson(write, WriteVO.class)))).build();
	}

	@POST
	@Path("/{chapterId}/capePic")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveChapterCape(@PathParam("bookId") String bookId, @PathParam("chapterId") Long chapterId, @Context HttpServletRequest req) {
		new BookService().saveChapterCape(bookId, chapterId, req);

		return Response.ok().build();
	}

	@GET
	@Path("/{chapterId}/capePic")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getChapterCape(@PathParam("bookId") Long bookId, @PathParam("chapterId") Long chapterId) {
		return Response.ok(new BookService().getChapterFromBook(bookId, chapterId).getCapeImageUrl()).build();
	}

	@DELETE
	@Path("/{chapterId}")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response publishBookFromUser(@PathParam("bookId") Long bookId, @PathParam("chapterId") Long chapterId) {
		new BookService().deleteChapterFromBook(bookId, chapterId);

		return Response.ok().build();
	}

}
