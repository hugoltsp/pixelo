package com.teles.yore.api.client;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.HystrixCommand;
import com.teles.yore.api.client.resource.YoreImageResource;
import com.teles.yore.domain.api.YoreImage;
import com.teles.yore.domain.api.YoreRequest;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Component
public class YoreClient implements YoreImageResource {

	private final String url;

	@Inject
	public YoreClient(Environment env) {
		this.url = env.getProperty("yore.api.url");
	}

	@Override
	public HystrixCommand<YoreImage> pixelate(YoreRequest req) {
		HystrixCommand<YoreImage> response = HystrixFeign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
				.target(YoreImageResource.class, this.url).pixelate(req);
		return response;
	}

}