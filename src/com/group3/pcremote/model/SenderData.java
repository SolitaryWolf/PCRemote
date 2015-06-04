package com.group3.pcremote.model;

import java.io.Serializable;

public class SenderData implements Serializable {
	private String command = "";
	private Object data = null;

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
