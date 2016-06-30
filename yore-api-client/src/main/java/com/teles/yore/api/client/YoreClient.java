package com.teles.yore.api.client;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.teles.yore.api.client.resource.YoreImageResource;
import com.teles.yore.domain.YoreImage;
import com.teles.yore.domain.YoreRequest;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Component
public class YoreClient implements YoreImageResource {

	private static final Logger log = LoggerFactory.getLogger(YoreClient.class);

	private final String url;
	private final int requestTimeout;

	@Inject
	public YoreClient(Environment env) {
		this.url = env.getProperty("yore.api.url");
		this.requestTimeout = env.getProperty("yore.client.request.timeout", int.class, 2000);
		log.info("Pointing client to {} with request timeout of {}", this.url, this.requestTimeout);
		this.config();
	}

	@Override
	public HystrixCommand<YoreImage> pixelate(YoreRequest req) {
		HystrixCommand<YoreImage> response = HystrixFeign.builder().encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder()).target(YoreImageResource.class, this.url).pixelate(req);
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