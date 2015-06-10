package com.group3.pcremote.model;

import java.io.Serializable;

import com.group3.pcremote.constant.MouseConstant;

public class MouseClick implements Serializable {
	private int buttonIndex;
	private int press;
	public MouseClick() {
		// TODO Auto-generated constructor stub
		buttonIndex = 1;
		press = MouseConstant.CLICK;
	}   
	public int getPress() {
		return press;
	}

	public void setPress(int press) {
		this.press = press;
	}

	public int getButtonIndex() {
		return buttonIndex;
	}

	public void setButtonIndex(int buttonIndex) {
		this.buttonIndex = buttonIndex;
	}
	

}
