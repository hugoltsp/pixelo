package com.teles.yore.api.tests;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.env.MockEnvironment;

import com.teles.yore.api.verticle.v1.YoreImageVerticle;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class ApiTests {

	private Vertx vertx;
	private int port;
	private String resource;

	@Before
	public void setup(TestContext context) throws IOException {
		this.vertx = Vertx.vertx();

		ServerSocket socket = new ServerSocket(0);
		this.port = socket.getLocalPort();
		socket.close();

		this.resource = "http://localhost:" + this.port + "/api/v1";

		MockEnvironment env = new MockEnvironment();
		env.setProperty("server.port", this.port + "");
		env.setProperty("yore.api.route.pixelate", "/api/v1/image");

		this.vertx.deployVerticle(new YoreImageVerticle(env), context.asyncAssertSuccess());
	}

	@Test
	public void testValidAndSuccesfulPixelate(TestContext context) throws Exception {
		
	}

}
