package br.com.tjgwp.view.utils;

import com.google.gson.GsonBuilder;

public class GsonUtils {

	public static String toJson(Object o) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		return gsonBuilder.create().toJson(o);
	}

}
