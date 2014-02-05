package br.com.tjgwp.business.service.text;

import br.com.tjgwp.business.entity.text.TextPost;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.service.SuperService;
import br.com.tjgwp.business.service.user.UserService;
import br.com.tjgwp.domain.text.TextPostDomain;
import br.com.tjgwp.domain.user.UserDomain;

import com.googlecode.objectify.Ref;

public class TextPostService extends SuperService {

	public TextPost save(TextPost textPost) {
		UserEntity loggedUser = new UserService().getLoggedUser(true);

		new TextPostDomain().save(textPost);
		loggedUser.getPostedTexts().add(Ref.create(textPost));
		new UserDomain().save(loggedUser);

		return textPost;
	}

}
