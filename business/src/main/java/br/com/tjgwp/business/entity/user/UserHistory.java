package br.com.tjgwp.business.entity.user;

import java.util.ArrayList;
import java.util.List;

import br.com.tjgwp.business.entity.SuperEntity;
import br.com.tjgwp.domain.SuperDomain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
	private String url;
	@Parent
	private Ref<UserEntity> userEntity;

	public UserHistory() {
		super();

		targets = new ArrayList<Ref<? extends SuperEntity>>();
	}

	public UserHistory(UserHistoryType type, UserEntity userEntity) {
		this();
		this.userEntity = Ref.create(userEntity);
		setDate(System.currentTimeMillis());
		setType(type);
	}

	public UserHistory(UserHistoryType type, Ref<? extends SuperEntity> targetRef, UserEntity userEntity) {
		this(type, userEntity);

		targets.add(targetRef);
	}

	public UserHistory(UserHistoryType type, List<Ref<? extends SuperEntity>> targetsRef, UserEntity userEntity) {
		this(type, userEntity);
		targets.addAll(targetsRef);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JsonObject toJson() {
		JsonObject history = new JsonObject();

		history.addProperty("url", url);
		history.addProperty("type", type.toString());
		history.addProperty("date", date);

		JsonArray targets = new JsonArray();
		history.add("targets", targets);

		for (Long target : new SuperDomain().getIds(this.targets))
			targets.add(new JsonPrimitive(target));
		
		return history;
	}

}
