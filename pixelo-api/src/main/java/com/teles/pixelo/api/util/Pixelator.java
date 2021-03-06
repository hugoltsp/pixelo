package com.teles.pixelo.api.util;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.teles.pixelo.domain.PixeloRequest;

public final class Pixelator {

	private Pixelator() {
	}

	public static byte[] pixelate(PixeloRequest request) throws IOException {
		byte[] imageToPixelate = request.getPixeloImage().getImage();
		int pixelSize = request.getPixelSize();
		String name = request.getPixeloImage().getName();
		String formatName = name.substring(name.lastIndexOf(".") + 1);

		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageToPixelate));
		Raster src = img.getData();
		WritableRaster dest = src.createCompatibleWritableRaster();
		for (int y = 0; y < src.getHeight(); y += pixelSize) {
			for (int x = 0; x < src.getWidth(); x += pixelSize) {
				double[] pixel = new double[3];
				pixel = src.getPixel(x, y, pixel);
				for (int yd = y; (yd < y + pixelSize) && (yd < dest.getHeight()); yd++) {
					for (int xd = x; (xd < x + pixelSize) && (xd < dest.getWidth()); xd++) {
						dest.setPixel(xd, yd, pixel);
					}
				}
			}
		}
		img.setData(dest);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, formatName, baos);
		return baos.toByteArray();
	}
}
