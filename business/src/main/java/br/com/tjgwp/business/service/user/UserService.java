package br.com.tjgwp.business.service.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.BookVO;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.business.service.UnauthorizedException;
import br.com.tjgwp.business.service.email.EmailService;
import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.domain.user.UserDomain;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Ref;

public class UserService extends SuperService {

	private UserDomain userDomain = new UserDomain();

	public UserEntity getLoggedUser() {
		return getLoggedUser(false);
	}

	public UserEntity getLoggedUser(boolean mustExist) throws UnauthorizedException {
		User user = getUser();
		if (user == null && mustExist)
			throw new UnauthorizedException();

		if (user == null)
			return new UserEntity(user);

		List<UserEntity> users = userDomain.findByEmail(user.getEmail());
		if (users.isEmpty()) {
			if (mustExist)
				throw new UnauthorizedException();
	
			UserEntity userEntity = new UserEntity(user);
			new UserDomain().save(userEntity);

			try {
				new EmailService().sendWelcomeMessage(userEntity);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return userEntity;
		}

		return users.get(0);
	}

	public String getLoginUrl(HttpServletRequest req) {
		if (getUser() == null)
			return UserServiceFactory.getUserService().createLoginURL("/");

		return "";
	}

	public String getLogoutUrl(HttpServletRequest req) {
		if (getUser() != null)
			return UserServiceFactory.getUserService().createLogoutURL("/");

		return "";
	}

	public UserEntity saveProfilePic(HttpServletRequest req) {
		ImageService imageService = new ImageService();
		BlobKey blobKey = imageService.getBlobFromRequest("profile", req);

		UserEntity user = getLoggedUser();
		if (blobKey != null) {
			user.updateProfile(blobKey);
			new UserDomain().save(user);
		}

		return user;
	}

	public UserEntity saveBackgroundPic(HttpServletRequest req) {
		ImageService imageService = new ImageService();
		BlobKey blobKey = imageService.getBlobFromRequest("background", req);

		UserEntity user = getLoggedUser();
		if (blobKey != null) {
			user.updateBackground(blobKey);
			new UserDomain().save(user);
		}

		return user;
	}

	public List<BookVO> getBooksFromUser(Long id) throws NotFoundException {
		UserEntity user = userDomain.findById(id);

		if (user == null)
			throw new NotFoundException();

		List<BookVO> books = new ArrayList<BookVO>();
		for (Ref<Book> ref : user.getBooks())
			books.add(new BookVO(ref.get()));

		return books;
	}

}
