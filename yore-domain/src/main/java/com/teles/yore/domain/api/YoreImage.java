package com.teles.yore.domain.api;

import java.util.Arrays;

public class YoreImage {

	private byte[] image;
	private String name;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getSize() {
		return image == null ? 0 : image.length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "YoreImage [getSize()=" + getSize() + ", getName()=" + getName() + "]";
	}

}
