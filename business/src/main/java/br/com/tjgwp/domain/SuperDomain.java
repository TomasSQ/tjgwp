package br.com.tjgwp.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.Image;
import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.entity.text.KeyWord;
import br.com.tjgwp.business.entity.user.UserEntity;
import br.com.tjgwp.business.entity.user.UserHistory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

public class SuperDomain {

	static {
		factory().register(SuperEntity.class);
		factory().register(UserEntity.class);
		factory().register(UserHistory.class);
		factory().register(KeyWord.class);
		factory().register(Chapter.class);
		factory().register(Book.class);
		factory().register(Image.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

	public <T extends SuperEntity> Key<T> save(T entity) {
		if (entity == null)
			throw new NotFoundException();

		ofy().save().entity(entity).now();
		entity.createUrl();
		return ofy().save().entity(entity).now();
	}

	public <T> T findById(Long id, Class<T> clazz) {
		return ofy().load().type(clazz).id(id).safe();
	}

	public <T> List<T> get(List<Ref<T>> refList) {
		List<T> list = new ArrayList<T>();

		for (Ref<T> ref : refList)
			list.add(ref.get());

		return list;
	}

	public List<Long> getIds(List<Ref<? extends SuperEntity>> targets) {
		List<Long> list = new ArrayList<Long>();

		for (Ref<? extends SuperEntity> ref : targets)
			list.add(ref.get().getId());

		return list;
	}

	public void remove(SuperEntity entity) {
		if (entity == null)
			return;
		ofy().delete().type(entity.getClass()).id(entity.getId()).now();
	}

	public void remove(SuperEntity parent, SuperEntity entity) {
		if (entity == null)
			return;
		ofy().delete().type(entity.getClass()).parent(parent).id(entity.getId()).now();
	}

	public void removeAll(SuperEntity parent, List<? extends SuperEntity> entities) {
		if (entities == null)
			return;

		for (SuperEntity entity : entities)
			remove(parent, entity);
	}
}
