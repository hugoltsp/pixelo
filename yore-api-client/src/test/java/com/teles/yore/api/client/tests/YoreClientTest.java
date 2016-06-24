package com.teles.yore.api.client.tests;

import java.io.FileOutputStream;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.teles.yore.api.client.YoreClient;
import com.teles.yore.domain.api.YoreImage;
import com.teles.yore.domain.api.YoreRequest;
import com.teles.yore.domain.api.YoreResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@SpringApplicationConfiguration(classes = { YoreClientTest.class })
@ComponentScan({"com.teles.yore"})
public class YoreClientTest {

	@Inject
	private Environment env;

	@Test
	public void test() throws Exception {
		YoreRequest req = new YoreRequest();
		YoreImage yoreImage = new YoreImage();

		yoreImage.setImage(IOUtils.toByteArray(Paths.get("D:/a.jpg").toUri()));
		yoreImage.setName("fodace.jpg");
		
		req.setPixelSize(5);
		req.setYoreImage(yoreImage);
		
		YoreClient yoreClient = new YoreClient(env);
		YoreResponse pixelate = yoreClient.pixelate(req);
		IOUtils.write(pixelate.getYoreImage().getImage(), new FileOutputStream("D:/" + pixelate.getYoreImage().getName()));
	}

}