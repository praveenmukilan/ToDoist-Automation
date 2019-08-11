package com.slate.api;

import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/**
 * ApiService class is to connect to the APIs and provide the response to the
 * caller.
 */
public class ApiService {

	public String postRequest(URL endPoint, String urlParameters) throws UnsupportedEncodingException {
		String response = "";
		HttpsURLConnection conn = null;
		byte[] postData = urlParameters.getBytes("UTF-8");
		try {
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
				System.out.println(getResponse(conn));
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.disconnect();

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

	public static String getRandomUuid() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		ApiService api = new ApiService();
		URL url;
		try {
			String projectName = "Project1";
			url = new URL("https://todoist.com/api/v7/sync");
			String urlParams = "token=19f95cac30653664f60a119ec4b3b1465bb1e9fa&"
					+ "commands=[{\"type\": \"project_add\", "
					+ "\"temp_id\": \"4df1e388-5ca6-453a-b0e8-662ebf373b6b\", \"uuid\": \"" + getRandomUuid()
					+ "\", \"args\": {\"name\": \"" + projectName + "\"}}]";
			api.postRequest(url, urlParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
