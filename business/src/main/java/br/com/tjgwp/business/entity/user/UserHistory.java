package br.com.tjgwp.business.entity.user;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.business.entity.text.Book;
import br.com.tjgwp.business.entity.text.Chapter;
import br.com.tjgwp.business.utils.URLMaker;
import br.com.tjgwp.domain.SuperDomain;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index = true)
public class UserHistory extends SuperEntity {

	@Index
	private Long date;
	private UserHistoryType type;
	@Load
	private List<Ref<? extends SuperEntity>> targets;
	@Parent
	private Ref<UserEntity> userEntity;

	public UserHistory() {
		super();

		targets = new ArrayList<Ref<? extends SuperEntity>>();
	}

	public UserHistory(UserHistoryType type, UserEntity userEntity) {
		this();
		this.userEntity = userEntity.getRef();
		setDate(System.currentTimeMillis());
		setType(type);
	}

	public UserHistory(UserHistoryType type, Ref<? extends SuperEntity> targetRef, UserEntity userEntity) {
		this(type, userEntity);

		targets.add(targetRef);
		if (type.isBook())
			setUrl(URLMaker.urlForBook((Book) targetRef.get()));
		else if (type.isUser())
			setUrl(URLMaker.urlForUser(userEntity));
		else if (type.isOtherUser())
			setUrl(URLMaker.urlForUser((UserEntity) targetRef.get()));
	}

	public UserHistory(UserHistoryType type, List<Ref<? extends SuperEntity>> targetsRef, UserEntity userEntity) {
		this(type, userEntity);

		targets.addAll(targetsRef);
		if (type.isChapter()) {
			if (targets.size() != 2)
				throw new IllegalArgumentException("chapter must have two targets ref only");

			setUrl(URLMaker.urlForChapter((Chapter) targets.get(1).get()));
		}
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public UserHistoryType getType() {
		return type;
	}

	public void setType(UserHistoryType type) {
		this.type = type;
	}

	public List<Ref<? extends SuperEntity>> getTargets() {
		return targets;
	}

	public void setTargets(List<Ref<? extends SuperEntity>> targets) {
		this.targets = targets;
	}

	public JsonObject toJson() {
		JsonObject history = new JsonObject();

		history.addProperty("url", getUrl());
		history.addProperty("type", type.toString());
		history.addProperty("date", date);

		JsonArray targets = new JsonArray();
		history.add("targets", targets);

		for (Long target : new SuperDomain().getIds(this.targets))
			targets.add(new JsonPrimitive(target));

		if (type.hasPic()) {
			history.addProperty("mainPic", toJsonMainPic());
			history.add("othersPics", toJsonOthersPics());
		}

		if (type.isOtherUser())
			history.add("user", new JsonParser().parse(((UserEntity) this.targets.get(0).get()).toJson()));
		else
			history.add("user", new JsonParser().parse(userEntity.get().toJson()));

		return history;
	}

	private String toJsonMainPic() {
		switch (type) {
			case NEW_BOOK:
			case CHANGED_BOOK:
			case CHANGED_BOOK_CAPE:
				return ((Book) targets.get(0).get()).getCape().getUrl();
			case NEW_CHAPTER:
			case CHANGED_CHAPTER:
			case CHANGED_CHAPTER_CAPE:
				return ((Chapter) targets.get(1).get()).getCape().getUrl();
			case CHANGED_CAPE_PICTURE:
				return ((UserEntity) targets.get(0).get()).getBackground().getUrl();
			case CHANGED_PROFILE_PICTURE:
				return ((UserEntity) targets.get(0).get()).getProfile().getUrl();
			case NEW_FOLLOWING:
			case NEW_FOLLOWER:
				return ((UserEntity) targets.get(0).get()).getProfile().getUrl();
			default:
				return null;
		}
	}

	private JsonElement toJsonOthersPics() {
		return new JsonArray();
	}

	@SuppressWarnings("unchecked")
	public Ref<UserHistory> getRef() {
		return Ref.create(this);
	}

	public void createUrl() {
	}

}
