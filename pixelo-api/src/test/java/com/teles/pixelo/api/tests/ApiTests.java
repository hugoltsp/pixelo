package com.teles.pixelo.api.tests;

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
import com.teles.pixelo.api.verticle.PixeloApiVerticle;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

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
		env.setProperty("pixelo.api.route.pixelate", API_V1_IMAGE);

		this.vertx.deployVerticle(new PixeloApiVerticle(env), context.asyncAssertSuccess());

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = newPort;
	}

	@Test
	public void testValidAndSuccesfulPixelate(TestContext context) throws Exception {
		PixeloRequest req = createRequest();
		PixeloImage img = RestAssured.given().body(Json.encodePrettily(req)).request().post(API_V1_IMAGE).andReturn().as(PixeloImage.class);

		Assert.assertNotNull(img);
		Assert.assertTrue(img.getImage() != null || img.getSize() > 0);
		Assert.assertEquals(req.getPixeloImage().getName(), img.getName());
	}

	@AfterClass
	public static void afterClass() {
		RestAssured.reset();
	}

	private static PixeloRequest createRequest() throws IOException {
		PixeloImage img = new PixeloImage();
		img.setName("sandy.jpg");
		img.setImage(Files.readAllBytes(Paths.get("../photo.jpg")));

		PixeloRequest req = new PixeloRequest();
		req.setPixelSize(6);
		req.setPixeloImage(img);
		return req;
	}

}