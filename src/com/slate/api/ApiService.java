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

/**
 * ApiService class is to connect to the APIs and provide the response to the
 * caller.
 */
public class ApiService {

	public String baseURL;
	public String token;

	public ApiService(String url, String tkn) {
		baseURL = url;
		token = tkn;
	}

	public String postRequest(String path, String commands) {
		String response = "";
		HttpsURLConnection conn = null;
		try {
			URL endPoint = new URL(baseURL + path);
			String urlParameters = "token=" + this.token + "&commands=" + commands;
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
				System.out.println(response);
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

			System.out.println("Server Response ... Success\n");
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

	public static boolean validateSyncStatus(JSONObject syncStatus, ArrayList uuidList) {
		boolean result = true;
		for (Object uuid : uuidList) {
			boolean itr_res;
			String value = syncStatus.get(((String) uuid)).toString();
			if (value.equalsIgnoreCase("ok")) {
				System.out.println(uuid + " is synced");
				itr_res = true;
			}
			else {
				System.out.println(uuid + " is not synced");
				itr_res = false;
			}
			result =  result && itr_res;
		}
		return result;
	}

	public String createProject(String projectName) {
		JSONObject args = new JSONObject().put("name", projectName);
		System.out.println(args.toString());
		Command cmd = new Command(Command.Types.project_add, args);
		Commands.addCommand(cmd);
		String prjtAddCommands = Commands.getCommandsAsJson();
		String response =  this.postRequest("/", prjtAddCommands);
		System.out.println(validateSyncStatus(getSyncStatus(response), Commands.getUuidList()));
		return "";
	}

	public static void main(String[] args) {
		ApiService api = new ApiService("https://todoist.com/api/v7/sync", "c7179ae59e4f823220c6980c8a0deeccdcc6761d");
		try {
			String projectName = "Praveen Project";
			api.createProject(projectName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
