package com.slate.api;

import java.util.ArrayList;
import org.json.JSONArray;

public class Commands {

	public static JSONArray cmdA = new JSONArray();
	public static ArrayList<Command> commandList = new ArrayList<Command>();
	public static ArrayList<String> uuidList = new ArrayList<String>();

	public static String getCommandsAsJson() {
		System.out.println(cmdA.toString());
		return cmdA.toString();
	}

	public static ArrayList<Command> getCommandsList() {
		return commandList;
	}

	public static ArrayList getUuidList() {
		return uuidList;
	}

	public static void addCommand(Command cmd) {
		cmdA.put(cmd.getCmdJson());
		commandList.add(cmd);
		uuidList.add(cmd.uuid);
	}
}
