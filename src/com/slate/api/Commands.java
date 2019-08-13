package com.slate.api;

import java.util.ArrayList;
import org.json.JSONArray;

public class Commands {

	public JSONArray cmdA;
	public ArrayList<Command> commandList;
	public ArrayList<String> uuidList;
	
	public Commands() {
		commandList = new ArrayList<Command>();
		uuidList = new ArrayList<String>();
		cmdA = new JSONArray();
	}

	public  String getCommandsAsJson() {
//		System.out.println(cmdA.toString());
		return "&commands=" + cmdA.toString();
	}

	public ArrayList<Command> getCommandsList() {
		return this.commandList;
	}

	public ArrayList getUuidList() {
		return this.uuidList;
	}

	public void addCommand(Command cmd) {
		this.cmdA.put(cmd.getCmdJson());
		this.commandList.add(cmd);
		this.uuidList.add(cmd.uuid);
	}
}
