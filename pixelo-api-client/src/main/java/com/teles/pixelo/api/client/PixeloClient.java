package com.teles.pixelo.api.client;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.teles.pixelo.api.client.resource.PixeloImageResource;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Component
public class PixeloClient implements PixeloImageResource {

	private static final Logger log = LoggerFactory.getLogger(PixeloClient.class);

	private final String url;
	private final int requestTimeout;

	@Inject
	public PixeloClient(Environment env) {
		this.url = env.getProperty("pixelo.client.api.url", String.class, "http://localhost:9090/api/v1");
		this.requestTimeout = env.getProperty("pixelo.client.request.timeout", int.class, 2000);
		log.info("Pointing client to {} with request timeout of {}", this.url, this.requestTimeout);
		this.config();
	}

	@Override
	public HystrixCommand<PixeloImage> pixelate(PixeloRequest req) {
		HystrixCommand<PixeloImage> response = HystrixFeign.builder().encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder()).target(PixeloImageResource.class, this.url).pixelate(req);
		return response;
	}

	private void config() {
		Method[] methods = this.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			ConfigurationManager.getConfigInstance().setProperty(
					String.format("hystrix.command.%s.execution.isolation.thread.timeoutInMilliseconds", methodName),
					this.requestTimeout);
		}
	}

}