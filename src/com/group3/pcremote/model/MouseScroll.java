package com.group3.pcremote.model;

import java.io.Serializable;

public class MouseScroll implements Serializable {
	int scrollValue = 0;
	
	public MouseScroll(int scrollValue) {
		this.scrollValue = scrollValue;
		// TODO Auto-generated constructor stub
	}

	public int getScrollValue() {
		return scrollValue;
	}
	public void setScrollValue(int scrollValue) {
		this.scrollValue = scrollValue;
	}
	
}
