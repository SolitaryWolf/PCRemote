package com.group3.pcremote.model;

public class TouchpadBackgroundDetail {
	private int imgId;
	private String imgName;

	public TouchpadBackgroundDetail(int imgId, String imgName) {
		this.imgId = imgId;
		this.imgName = imgName;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

}
