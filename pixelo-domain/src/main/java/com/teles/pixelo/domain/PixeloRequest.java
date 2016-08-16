package com.teles.pixelo.domain;

public class PixeloRequest {

	private PixeloImage pixeloImage;
	private int pixelSize;

	public PixeloImage getPixeloImage() {
		return pixeloImage;
	}

	public void setPixeloImage(PixeloImage pixeloImage) {
		this.pixeloImage = pixeloImage;
	}

	public int getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
	}

	@Override
	public String toString() {
		return "PixeloRequest [pixeloImage=" + pixeloImage + ", pixelSize=" + pixelSize + "]";
	}

}
