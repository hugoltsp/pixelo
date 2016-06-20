package com.teles.yore.domain.api;

public class YoreRequest {

	private YoreImage yoreImage;
	private int pixelSize;

	public YoreImage getYoreImage() {
		return yoreImage;
	}

	public void setYoreImage(YoreImage yoreImage) {
		this.yoreImage = yoreImage;
	}

	public int getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
	}

}
