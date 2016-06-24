package com.teles.yore.api.client;

import javax.inject.Inject;

import org.springframework.core.env.Environment;

import com.teles.yore.api.client.resource.YoreImageResource;
import com.teles.yore.domain.api.YoreRequest;
import com.teles.yore.domain.api.YoreResponse;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class YoreClient implements YoreImageResource {

	private final String url;

	@Inject
	public YoreClient(Environment env) {
		this.url = env.getProperty("yore.api.url");
	}

	@Override
	public YoreResponse pixelate(YoreRequest req) {
		YoreResponse response = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
				.target(YoreImageResource.class, this.url).pixelate(req);
		return response;
	}

}