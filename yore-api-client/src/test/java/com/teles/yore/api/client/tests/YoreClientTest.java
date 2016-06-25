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
	public void test() throws Exception {
		YoreRequest req = new YoreRequest();
		YoreImage yoreImage = new YoreImage();

		yoreImage.setImage(IOUtils.toByteArray(Paths.get("/home/hugo/Imagens/Wallpapers/519345.jpg").toUri()));
		yoreImage.setName("fodace.jpg");

		req.setPixelSize(5);
		req.setYoreImage(yoreImage);

		YoreClient yoreClient = new YoreClient(env);
		HystrixCommand<YoreImage> cmd = yoreClient.pixelate(req);
		YoreImage pixelate = cmd.queue().get();
		IOUtils.write(pixelate.getImage(), new FileOutputStream("/home/hugo/yore/test/" + pixelate.getName()));
	}

}