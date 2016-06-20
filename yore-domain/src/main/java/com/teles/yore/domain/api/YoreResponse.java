package com.teles.yore.domain.api;

public class YoreResponse {

	private YoreImage yoreImage;
	private String message;

	public YoreImage getYoreImage() {
		return yoreImage;
	}

	public void setYoreImage(YoreImage yoreImage) {
		this.yoreImage = yoreImage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "YoreResponse [yoreImage=" + yoreImage + ", message=" + message + "]";
	}

}
