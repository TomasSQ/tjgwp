package br.com.tjgwp.view.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

	public String toJson(Object o) {
		return new GsonBuilder().create().toJson(o);
	}

	public <T> T fromJson(String textPost, Class<T> clazz) {
		return new Gson().fromJson(textPost, clazz);
	}
}
