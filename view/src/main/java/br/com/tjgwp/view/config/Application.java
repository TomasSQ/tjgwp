package br.com.tjgwp.view.config;

import java.util.HashSet;
import java.util.Set;

import br.com.tjgwp.view.rs.image.ImageRS;
import br.com.tjgwp.view.rs.search.SearchRS;
import br.com.tjgwp.view.rs.user.UserRS;
import br.com.tjgwp.view.text.BookRS;
import br.com.tjgwp.view.text.ChapterRS;

public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(UserRS.class);
		classes.add(ImageRS.class);
		classes.add(BookRS.class);
		classes.add(ChapterRS.class);
		classes.add(SearchRS.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();

		return singletons;
	}

}
