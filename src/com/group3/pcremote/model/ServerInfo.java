package com.group3.pcremote.model;

import java.io.Serializable;

public class ServerInfo implements Serializable {
	private String serverName;
	private String serverIP;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

}
