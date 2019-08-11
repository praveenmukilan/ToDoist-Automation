package com.slate.api;

import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

	public String createProject(String projectName) {
		JSONObject args = new JSONObject().put("name", projectName);
		System.out.println(args.toString());
		String prjtAddCommand = Commands.getCommands(Commands.Types.project_add, args);
		return this.postRequest("/", prjtAddCommand);
	}

	public static void main(String[] args) {
		ApiService api = new ApiService("https://todoist.com/api/v7/sync", "19f95cac30653664f60a119ec4b3b1465bb1e9fa");
		try {
			String projectName = "Praveen Project";
//			String urlParams = "token=19f95cac30653664f60a119ec4b3b1465bb1e9fa&"
//					+ "commands=[{\"type\": \"project_add\", "
//					+ "\"temp_id\": \"4df1e388-5ca6-453a-b0e8-662ebf373b6b\", \"uuid\": \"" + getRandomUuid()
//					+ "\", \"args\": {\"name\": \"" + projectName + "\"}}]";

			api.createProject(projectName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
