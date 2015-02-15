package br.com.tjgwp.business.service.user;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.entity.user.UserHistory;
import br.com.tjgwp.business.entity.user.UserHistoryType;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.business.service.UnauthorizedException;
import br.com.tjgwp.business.service.email.EmailService;
import br.com.tjgwp.business.service.image.ImageService;
import br.com.tjgwp.domain.user.UserDomain;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonArray;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Work;

public class UserService extends SuperService {

	private UserDomain userDomain = new UserDomain();

	public UserEntity getLoggedUser() {
		return getLoggedUser(false);
	}

	public UserEntity getUser(Long id) {
		User user = getUser();
		if (user == null)
			throw new UnauthorizedException();

		return userDomain.findById(id);
	}

	public UserEntity getMe() {
		User user = getUser();
		if (user == null)
			throw new UnauthorizedException();

		List<UserEntity> users = userDomain.findByEmail(user.getEmail());
		if (users.isEmpty())
			throw new UnauthorizedException();

		return users.get(0);
	}

	public UserEntity getLoggedUser(boolean mustExist) throws UnauthorizedException {
		User user = getUser();
		if (user == null && mustExist)
			throw new UnauthorizedException();

		if (user == null)
			return new UserEntity();

		List<UserEntity> users = userDomain.findByEmail(user.getEmail());
		if (users.isEmpty()) {
			if (mustExist)
				throw new UnauthorizedException();
	
			UserEntity userEntity = newUserEntity(user);

			try {
				new EmailService().sendWelcomeMessage(userEntity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return userEntity;
		}

		return users.get(0);
	}

	private UserEntity newUserEntity(User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(user.getEmail());
		userEntity.setNickname(user.getNickname());

		Chapter chapter = new Chapter();
		chapter.setTitle("Chapter 1");
		userDomain.save(chapter);

		Book book = new Book();
		book.setTitle("My first Book");
		Ref<Chapter> chapterRef = Ref.create(chapter);
		book.getChapters().add(chapterRef);
		userDomain.save(book);

		Ref<Book> bookRef = Ref.create(book);
		userEntity.getBooks().add(bookRef);

		userDomain.save(userEntity);

		createNewUserHistory(userEntity, chapterRef, bookRef);

		return userEntity;
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
		final BlobKey blobKey = new ImageService().getBlobFromRequest("profile", req);

		return ObjectifyService.run(new Work<UserEntity>() {
			@Override
			public UserEntity run() {
				UserEntity user = getLoggedUser(true);

				if (blobKey != null) {
					user.updateProfile(blobKey);
					createNewUserHistory(user, user.getProfileRef(), UserHistoryType.CHANGED_PROFILE_PICTURE);
					new UserDomain().save(user.getProfile());
					new UserDomain().save(user);
				}

				return user;
			}
		});
	}

	public UserEntity saveBackgroundPic(HttpServletRequest req) {
		final BlobKey blobKey = new ImageService().getBlobFromRequest("background", req);

		return ObjectifyService.run(new Work<UserEntity>() {
			@Override
			public UserEntity run() {
				UserEntity user = getLoggedUser(true);

				if (blobKey != null) {
					user.updateBackground(blobKey);
					createNewUserHistory(user, user.getBackgroundRef(), UserHistoryType.CHANGED_CAPE_PICTURE);
					new UserDomain().save(user.getBackground());
					new UserDomain().save(user);
				}

				return user;
			}
		});
	}

	public UserEntity getUserOrMe(Long id) {
		return id == null ? getLoggedUser(true) : userDomain.findById(id, UserEntity.class);
	}

	public List<UserHistory> getLastestHistoryFromUser(Long id) {
		return userDomain.getLastedHistoryFromUser(getUserOrMe(id));
	}

	public String getLastestHistoryFromUserAsJson(Long id) {
		List<UserHistory> history = userDomain.getLastedHistoryFromUser(getUserOrMe(id));

		JsonArray historyJson = new JsonArray();
		for (UserHistory hist : history)
			historyJson.add(hist.toJson());

		return historyJson.toString();
	}

	public void createNewUserHistory(UserEntity userEntity, Ref<? extends SuperEntity> entityRef, UserHistoryType type) {
		newUserHistory(type, entityRef, userEntity);
	}

	public void createNewUserHistory(UserEntity userEntity, Ref<Chapter> chapterRef, Ref<Book> bookRef) {
		newUserHistory(UserHistoryType.SIGNED_IN, userEntity);
	}

	public void createNewBookUserHistory(UserEntity userEntity, Ref<Book> bookRef) {
		newUserHistory(UserHistoryType.NEW_BOOK, bookRef, userEntity);
	}

	public void createNewChapterUserHistory(UserEntity userEntity, Ref<Book> bookRef, Ref<Chapter> chapterRef) {
		newUserHistory(UserHistoryType.NEW_CHAPTER, Arrays.asList(bookRef, chapterRef), userEntity);
	}

	@SuppressWarnings("unchecked")
	private Ref<UserHistory> newUserHistory(UserHistoryType userHistoryType, UserEntity userEntity) {
		return (Ref<UserHistory>) Ref.create(userDomain.save(new UserHistory(userHistoryType, userEntity)));
	}

	@SuppressWarnings("unchecked")
	private Ref<UserHistory> newUserHistory(UserHistoryType userHistoryType, Ref<? extends SuperEntity> bookRef, UserEntity userEntity) {
		return (Ref<UserHistory>) Ref.create(userDomain.save(new UserHistory(userHistoryType, bookRef, userEntity)));
	}

	@SuppressWarnings("unchecked")
	private Ref<UserHistory> newUserHistory(UserHistoryType userHistoryType, List<Ref<? extends SuperEntity>> targets, UserEntity userEntity) {
		return (Ref<UserHistory>) Ref.create(userDomain.save(new UserHistory(userHistoryType, targets, userEntity)));
	}

}
