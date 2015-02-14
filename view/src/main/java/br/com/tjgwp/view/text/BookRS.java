package br.com.tjgwp.view.text;

import javax.servlet.http.HttpServletRequest;
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

import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.text.ChapterVO;
import br.com.tjgwp.business.service.text.BookService;
import br.com.tjgwp.business.service.text.WriteVO;
import br.com.tjgwp.view.SuperRS;

@Path("/book")
public class BookRS extends SuperRS {

	@Path("/")
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateBook(@FormParam("book") String book) {
		new BookService().saveBook(gsonUtils.fromJson(book, BookVO.class));

		return Response.ok().build();
	}

	@POST
	@Path("/{bookId}/capePic")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveBookCape(@PathParam("bookId") String bookId, @Context HttpServletRequest req) {
		new BookService().saveBookCape(bookId, req);

		return Response.ok().build();
	}

	@GET
	@Path("/{bookId}/capePic")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getBookCape(@PathParam("bookId") String bookId) {
		return Response.ok(new BookService().getBook(bookId).getCape().getUrl()).build();
	}

	@Path("/{bookId}/chapter/")
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewChapter(@PathParam("bookId") String bookId, @FormParam("chapter") String chapter) {
		new BookService().saveChapter(bookId, gsonUtils.fromJson(chapter, ChapterVO.class));

		return Response.ok().build();
	}

	@Path("/{bookId}/chapter/{chapter}")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveNewBook(@PathParam("bookId") String bookId, @PathParam("chapter") String chapter, @FormParam("write") String write) {
		return Response.ok(gsonUtils.toJson(new BookService().save(bookId, chapter, gsonUtils.fromJson(write, WriteVO.class)))).build();
	}

	@Path("/{bookId}/chapter/{chapterId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getChapterFromBook(@PathParam("bookId") Long book, @PathParam("chapterId") Long chapter, @FormParam("write") String write) {
		return Response.ok(gsonUtils.toJson(new BookService().getChapterFromBook(book, chapter))).build();
	}

	@POST
	@Path("/{bookId}/chapter/{chapterId}/capePic")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response saveChapterCape(@PathParam("bookId") String bookId, @PathParam("chapterId") Long chapterId, @Context HttpServletRequest req) {
		new BookService().saveChapterCape(bookId, chapterId, req);

		return Response.ok().build();
	}

	@GET
	@Path("/{bookId}/chapter/{chapterId}/capePic")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getChapterCape(@PathParam("bookId") Long bookId, @PathParam("chapterId") Long chapterId) {
		return Response.ok(new BookService().getChapterFromBook(bookId, chapterId).getCapeImageUrl()).build();
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
	@Path("/{bookId}")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response getBookFromUser(@PathParam("bookId") Long bookId) {
		return Response.ok(gsonUtils.toJson(new BookService().getFullBook(null, bookId))).build();
	}

	@PUT
	@Path("/{bookId}/publish")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public Response publishBookFromUser(@PathParam("bookId") Long bookId) {
		new BookService().publishBook(null, bookId);

		return Response.ok().build();
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
