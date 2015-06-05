package com.group3.pcremote.model;

import java.io.Serializable;

public class SenderData implements Serializable {
	private String command = "";
	private Object data = null;
	
	
	public int x = 20;
	public double y = 3.543;
	public String s = "sdfdsjskdahfkjahdsfjkshdfjkskj kjdfhfdjkafhsajkf hddalfhjbjhggjhdhl";

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
