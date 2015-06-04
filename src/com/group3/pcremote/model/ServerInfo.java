package com.group3.pcremote.model;

import java.io.Serializable;

public class ServerInfo implements Serializable {
	private String serverIP;
	private String serverName;

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}
