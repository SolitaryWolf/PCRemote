package com.group3.pcremote.model;

public class ServerInfoHistory {
	private String serverIP;
	private String serverName;
	private String connectedDate;

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

	public String getConnectedDate() {
		return connectedDate;
	}

	public void setConnectedDate(String connectedDate) {
		this.connectedDate = connectedDate;
	}
}
