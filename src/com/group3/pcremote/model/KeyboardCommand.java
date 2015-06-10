package com.group3.pcremote.model;

import java.io.Serializable;

public class KeyboardCommand implements Serializable {
	private int keyboardCode;
	private int press;
	public int getPress() {
		return press;
	}
	public void setPress(int press) {
		this.press = press;
	}
	public int getKeyboardCode() {
		return keyboardCode;
	}
	public void setKeyboardCode(int keyboardCode) {
		//keyboard code meaning in KeyboardConstants.java file.
		this.keyboardCode = keyboardCode; 
	}
	

}
