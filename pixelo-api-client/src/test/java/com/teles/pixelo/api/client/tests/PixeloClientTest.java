package com.teles.pixelo.api.client.tests;

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
import com.teles.pixelo.api.client.PixeloClient;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@SpringApplicationConfiguration(classes = { PixeloClientTest.class })
@ComponentScan({ "com.teles.pixelo" })
public class PixeloClientTest {

	@Inject
	private Environment env;

	@Test
	@Ignore
	public void test() throws Exception {
		PixeloRequest req = new PixeloRequest();
		PixeloImage image = new PixeloImage();

		image.setImage(Files.readAllBytes(Paths.get("../photo.jpg")));
		image.setName("photo.jpg");

		req.setPixelSize(5);
		req.setPixeloImage(image);

		PixeloClient client = new PixeloClient(env);
		HystrixCommand<PixeloImage> cmd = client.pixelate(req);
		PixeloImage pixelate = cmd.queue().get();

		Assert.assertTrue(pixelate.getSize() > 0);
	}

}