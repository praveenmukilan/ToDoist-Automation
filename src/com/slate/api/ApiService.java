package com.slate.api;

import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * ApiService class is to connect to the APIs and provide the response to the
 * caller.
 */
public class ApiService {

	public String baseURL;
	public String token;
	private Command cmd;
	private Commands cmds;

	public ApiService(String url, String tkn) {
		baseURL = url;
		token = tkn;
	}

	public String postRequest(String path, String formdata) {
		String response = "";
		HttpsURLConnection conn = null;
		try {
			URL endPoint = new URL(baseURL + path);
			String urlParameters = "token=" + this.token + formdata;
			byte[] postData = urlParameters.getBytes("UTF-8");
			conn = (HttpsURLConnection) endPoint.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(postData);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + conn.getResponseCode() + conn.getResponseMessage());
			} else {
				response = getResponse(conn);
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public static String getResponse(HttpsURLConnection conn) {
		BufferedReader br;
		String output;
		String response = "";
		try {
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				response += output;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public static JSONObject getSyncStatus(String response) {
		return new JSONObject(response).getJSONObject("sync_status");
	}

	public static boolean validateSyncStatus(JSONObject syncStatus, ArrayList<String> uuidList) {
		boolean result = true;
		for (Object uuid : uuidList) {
			boolean itr_res;
			String value = syncStatus.get(((String) uuid)).toString();
			if (value.equalsIgnoreCase("ok")) {
				itr_res = true;
			} else {
				itr_res = false;
			}
			result = result && itr_res;
		}
		return result;
	}

	public String createProject(String projectName) {
		JSONObject args = new JSONObject().put("name", projectName);
		cmd = new Command(Command.Types.project_add, args);
		cmds = new Commands();
		cmds.addCommand(cmd);
		String prjtAddCommands = cmds.getCommandsAsJson();
		String response = this.postRequest("/", prjtAddCommands);
		validateSyncStatus(getSyncStatus(response), cmds.getUuidList());
		return "";
	}

	public String getTaskId(String taskName) {
		String formdata = "&sync_token=\"*\"&resource_types=[\"items\"]";
		String response = this.postRequest("/", formdata);
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(response);
		JsonArray itemA = (JsonArray) json.get("items");

		for (JsonElement je : itemA) {
			JsonObject jo = (JsonObject) je;
			String actual = jo.get("content").toString();
			actual = actual.replaceAll("^\"|\"$", "");
			if (actual.equals(taskName)) {
				return jo.get("id").toString();
			}
		}
		return null;
	}

	public void uncompleteTasks(String[] taskIds) {
		StringBuffer ids = new StringBuffer("[");
		for (String id : taskIds) {
			ids.append(id);
		}
		ids.append("]");
		JSONObject args = new JSONObject().put("ids", ids.toString());
		Command cmd = new Command(Command.Types.item_uncomplete, args);
		cmds = new Commands();
		cmds.addCommand(cmd);
		String itemUncompleteCommands = cmds.getCommandsAsJson();
		String response = this.postRequest("/", itemUncompleteCommands);
		validateSyncStatus(getSyncStatus(response), cmds.getUuidList());
	}

}
