package com.teles.yore.domain.api;

import java.util.Arrays;

public class YoreImage {

	private byte[] image;
	private int size;
	private String name;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "YoreImage [image=" + Arrays.toString(image) + ", size=" + size + ", name=" + name + "]";
	}

}
