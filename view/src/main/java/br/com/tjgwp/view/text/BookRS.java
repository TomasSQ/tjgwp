package br.com.tjgwp.view.text;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.service.text.BookService;
import br.com.tjgwp.business.service.text.WriteVO;
import br.com.tjgwp.view.SuperRS;

@Path("/book")
public class BookRS extends SuperRS {

	@Path("/")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewBook(@FormParam("book") String book) {
		new BookService().saveBook(gsonUtils.fromJson(book, BookVO.class));

		return Response.ok().build();
	}

	@Path("/{bookId}/chapter")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewChapter(@PathParam("bookId") String bookId, @FormParam("chapter") String chapter) {
		new BookService().saveChapter(bookId, gsonUtils.fromJson(chapter, ChapterVO.class));

		return Response.ok().build();
	}

	@Path("/{book}/chapter/{chapter}")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewBook(@PathParam("book") String book, @PathParam("chapter") String chapter, @FormParam("write") String write) {
		return Response.ok(gsonUtils.toJson(new BookService().save(book, chapter, gsonUtils.fromJson(write, WriteVO.class)))).build();
	}

	@Path("/{bookId}/chapter/{chapterId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getChapterFromBook(@PathParam("bookId") Long book, @PathParam("chapterId") Long chapter, @FormParam("write") String write) {
		return Response.ok(gsonUtils.toJson(new BookService().getChapterFromBook(book, chapter))).build();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getBooksFromUser() throws com.googlecode.objectify.NotFoundException {
		return Response.ok(gsonUtils.toJson(new BookService().getBooksFromUser(null))).build();
	}

	@GET
	@Path("/fromUser/{userId}")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getBooksFromUser(@PathParam("userId") Long userId) throws com.googlecode.objectify.NotFoundException {
		return Response.ok(gsonUtils.toJson(new BookService().getBooksFromUser(userId))).build();
	}

	@GET
	@Path("/fromUser/{userId}/{bookId}")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getBookFromUser(@PathParam("userId") Long userId, @PathParam("bookId") Long bookId) {
		return Response.ok(gsonUtils.toJson(new BookService().getFullBook(userId, bookId))).build();
	}

	@GET
	@Path("/write")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getDataBookFromUser() {
		return Response.ok(gsonUtils.toJson(new BookService().getWriteVO())).build();
	}
}
