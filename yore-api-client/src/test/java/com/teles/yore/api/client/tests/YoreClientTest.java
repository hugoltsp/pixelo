package com.teles.yore.api.client.tests;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.netflix.hystrix.HystrixCommand;
import com.teles.yore.api.client.YoreClient;
import com.teles.yore.domain.YoreImage;
import com.teles.yore.domain.YoreRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@SpringApplicationConfiguration(classes = { YoreClientTest.class })
@ComponentScan({ "com.teles.yore" })
public class YoreClientTest {

	@Inject
	private Environment env;

	@Test
	@Ignore
	public void test() throws Exception {
		YoreRequest req = new YoreRequest();
		YoreImage yoreImage = new YoreImage();

		yoreImage.setImage(Files.readAllBytes(Paths.get("../photo.jpg")));
		yoreImage.setName("photo.jpg");

		req.setPixelSize(5);
		req.setYoreImage(yoreImage);

		YoreClient yoreClient = new YoreClient(env);
		HystrixCommand<YoreImage> cmd = yoreClient.pixelate(req);
		YoreImage pixelate = cmd.queue().get();

		Assert.assertTrue(pixelate.getSize() > 0);
	}

}