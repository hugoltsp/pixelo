package com.teles.yore.api.tests;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.env.MockEnvironment;

import com.jayway.restassured.RestAssured;
import com.teles.yore.api.verticle.YoreApiVerticle;
import com.teles.yore.domain.YoreImage;
import com.teles.yore.domain.YoreRequest;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class ApiTests {

	private static final String API_V1_IMAGE = "/api/v1/image";
	private Vertx vertx;

	@Before
	public void setup(TestContext context) throws IOException {
		this.vertx = Vertx.vertx();

		ServerSocket socket = new ServerSocket(0);
		int newPort = socket.getLocalPort();
		socket.close();

		MockEnvironment env = new MockEnvironment();
		env.setProperty("server.port", newPort + "");
		env.setProperty("yore.api.route.pixelate", API_V1_IMAGE);

		this.vertx.deployVerticle(new YoreApiVerticle(env), context.asyncAssertSuccess());

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = newPort;
	}

	@Test
	public void testValidAndSuccesfulPixelate(TestContext context) throws Exception {
		YoreRequest yoreRequest = createRequest();
		YoreImage yoreImage = RestAssured.given().body(Json.encodePrettily(yoreRequest)).request().post(API_V1_IMAGE).andReturn().as(YoreImage.class);

		Assert.assertNotNull(yoreImage);
		Assert.assertTrue(yoreImage.getImage() != null || yoreImage.getSize() > 0);
		Assert.assertEquals(yoreRequest.getYoreImage().getName(), yoreImage.getName());
	}

	@AfterClass
	public static void afterClass() {
		RestAssured.reset();
	}

	private static YoreRequest createRequest() throws IOException {
		YoreImage yoreImage = new YoreImage();
		yoreImage.setName("sandy.jpg");
		yoreImage.setImage(Files.readAllBytes(Paths.get("src/test/resources/photo.jpg")));

		YoreRequest yoreRequest = new YoreRequest();
		yoreRequest.setPixelSize(6);
		yoreRequest.setYoreImage(yoreImage);
		return yoreRequest;
	}

}