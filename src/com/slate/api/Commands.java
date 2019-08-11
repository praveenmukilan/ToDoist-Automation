package com.slate.api;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class Commands {
	
	public static String getCommands(Types type, JSONObject args) {
		return new JSONArray().put(new JSONObject()
								.put("type", type)
								.put("temp_id", getRandomUuid())
								.put("uuid", getRandomUuid())
								.put("args", args)
							).toString();
	}
	
	public static String getRandomUuid() {
		return UUID.randomUUID().toString();
	}
	
	enum Types {
		project_add
	}

}
