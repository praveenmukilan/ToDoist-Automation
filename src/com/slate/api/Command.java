package com.slate.api;

import java.util.UUID;
import org.json.JSONObject;

/**
 * Command class is to build various command arguments for the REST clients to consume.
 * @author praveenms
 *
 */
public class Command {
		
		Types type;
		String temp_id;
		String uuid;
		JSONObject args;
		JSONObject cmdJson;
		
		Command(Types type, JSONObject args){
			this.type = type;
			this.temp_id = this.getRandomUuid();
			this.uuid = this.getRandomUuid();
			this.args = args;
			this.setCmdJson();
		}
		
		public void setCmdJson() {
			this.cmdJson = new JSONObject()
					.put("type", type)
					.put("temp_id", this.temp_id)
					.put("uuid", this.uuid)
					.put("args", args);
		}
		
		public JSONObject getCmdJson() {
			return this.cmdJson;
		}
		
		public String getRandomUuid() {
			return UUID.randomUUID().toString();
		}
		
		enum Types {
			project_add,
			item_uncomplete
		}
		

	
}
