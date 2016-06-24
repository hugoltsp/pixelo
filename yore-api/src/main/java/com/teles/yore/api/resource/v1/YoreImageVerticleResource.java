package com.teles.yore.api.resource.v1;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.teles.yore.api.util.Pixelator;
import com.teles.yore.domain.api.YoreImage;
import com.teles.yore.domain.api.YoreRequest;
import com.teles.yore.domain.api.YoreResponse;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Component
public class YoreImageVerticleResource extends AbstractVerticle {

	private static final String SERVER_PORT = "server.port";

	private final int serverPort;
	private final String post;

	@Inject
	public YoreImageVerticleResource(Environment env) {
		this.serverPort = env.getProperty(SERVER_PORT, int.class, 8080);
		this.post = env.getProperty("yore.api.route.pixelate", String.class);
	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		Router router = Router.router(this.vertx);
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
		YoreRequest request = Json.decodeValue(routingContext.getBodyAsString(), YoreRequest.class);
		
		try {
			byte[] pixelate = Pixelator.pixelate(request.getYoreImage().getImage(), request.getPixelSize());
			YoreResponse response = new YoreResponse();

			YoreImage yoreImage = new YoreImage();
			yoreImage.setImage(pixelate);
			yoreImage.setName(request.getYoreImage().getName());

			response.setMessage("Ok");
			response.setYoreImage(yoreImage);

			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(HttpResponseStatus.OK.code()).end(Json.encodePrettily(response));

		} catch (IOException e) {
			e.printStackTrace();
			YoreResponse response = new YoreResponse();
			response.setMessage("Error");
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(Json.encodePrettily(response));
		}
	}

}
