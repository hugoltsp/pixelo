package com.teles.yore.api.client.resource;

import com.netflix.hystrix.HystrixCommand;
import com.teles.yore.domain.api.YoreRequest;
import com.teles.yore.domain.api.YoreResponse;

import feign.Headers;
import feign.RequestLine;

public interface YoreImageResource {

	@RequestLine("POST /image")
	@Headers("Content-Type: application/json")
	HystrixCommand<YoreResponse> pixelate(YoreRequest req);
	
}
