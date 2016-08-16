package com.teles.pixelo.api.verticle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.teles.pixelo.api.util.Pixelator;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

@Component
public class PixeloApiVerticle extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(PixeloApiVerticle.class);

	private static final String SERVER_PORT = "server.port";

	private final int serverPort;
	private final String post;

	@Inject
	public PixeloApiVerticle(Environment env) {
		this.serverPort = env.getProperty(SERVER_PORT, int.class, 9090);
		this.post = env.getProperty("pixelo.api.route.pixelate", String.class);
	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		Router router = Router.router(this.vertx);

		router.route(this.post + "*").handler(BodyHandler.create());
		router.post(this.post).handler(this::pixelate);
		this.vertx.createHttpServer().requestHandler(router::accept)
				.listen(config().getInteger(SERVER_PORT, this.serverPort), result -> {
					if (result.succeeded()) {
						startFuture.complete();
					} else {
						startFuture.fail(result.cause());
					}
				});
	}

	private final void pixelate(RoutingContext routingContext) {
		String bodyAsString = routingContext.getBodyAsString();
		PixeloRequest request = Json.decodeValue(bodyAsString, PixeloRequest.class);

		try {

			byte[] pixelate = Pixelator.pixelate(request);

			PixeloImage pixeloImage = new PixeloImage();
			pixeloImage.setImage(pixelate);
			pixeloImage.setName(request.getPixeloImage().getName());

			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(HttpResponseStatus.OK.code()).end(Json.encodePrettily(pixeloImage));
		} catch (Exception e) {
			log.info("Image - Size: {}, PixelSize: {}, Name: {}", request.getPixeloImage().getSize(),
					request.getPixelSize(), request.getPixeloImage().getName());
			log.info("Error: ", e);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
					.end(HttpResponseStatus.INTERNAL_SERVER_ERROR.reasonPhrase());
		}
	}

}