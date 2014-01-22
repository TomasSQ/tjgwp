package br.com.tjgwp.business.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.Service;
import br.com.tjgwp.domain.user.UserDomain;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class UserService extends Service {

	public UserEntity getLoggedUser() {
		User user = getUser();
		if (user == null)
			return new UserEntity(user);

		List<UserEntity> users = UserDomain.findUsersByEmail(user.getEmail());
		if (users.isEmpty()) {
			UserEntity userEntity = new UserEntity(user);
			new UserDomain().save(userEntity);

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
		Map<String, List<BlobKey>> blobs = BlobstoreServiceFactory.getBlobstoreService().getUploads(req);
		BlobKey blobKey = blobs.get("profile").get(0);

		UserEntity user = getLoggedUser();
		if (blobKey != null) {
			user.setProfile(blobKey);
			new UserDomain().save(user);
		}

		return user;
	}

}
