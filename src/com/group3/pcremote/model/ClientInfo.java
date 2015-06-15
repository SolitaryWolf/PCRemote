package com.group3.pcremote.model;

import java.io.Serializable;

public class ClientInfo implements Serializable {
	private String clientName;
	private String clientIP;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

}
