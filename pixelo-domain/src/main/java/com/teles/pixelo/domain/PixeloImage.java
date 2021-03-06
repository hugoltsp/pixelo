package com.teles.pixelo.domain;

public class PixeloImage {

	private byte[] image;
	private String name;
	private int size;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
		this.size = image == null ? 0 : image.length;
	}

	public int getSize() {
		return this.size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PixeloImage [getSize()=" + getSize() + ", getName()=" + getName() + "]";
	}

}
