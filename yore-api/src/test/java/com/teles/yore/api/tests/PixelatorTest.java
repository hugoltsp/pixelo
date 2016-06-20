package com.teles.yore.api.tests;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.teles.yore.api.util.Pixelator;

public class PixelatorTest {

	@Test
	public void testPixelator() throws Exception {
		byte[] imagePixelated = Pixelator.pixelate(IOUtils.toByteArray(Paths.get("E:/Imagens/Wallpaper/wallpaper-2774108.png").toUri()), 5);
		Files.write(Paths.get("C:/Users/AAA/Desktop/image-pixelated.jpg"), imagePixelated);
	}

}
