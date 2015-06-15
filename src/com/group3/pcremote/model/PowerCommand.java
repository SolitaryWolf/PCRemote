package com.group3.pcremote.model;

import java.io.Serializable;


public class PowerCommand implements Serializable{
	String powerConstant = null;
	public PowerCommand() {
		// TODO Auto-generated constructor stub
	}
	public PowerCommand(String powerConstant) {
		this.powerConstant = powerConstant;
	}
	public String getPowerConstant() {
		return powerConstant;
	}
	public void setPowerConstant(String powerConstant) {
		this.powerConstant = powerConstant;
	}
	
}
