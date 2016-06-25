package com.teles.yore.api.client.resource;

import com.netflix.hystrix.HystrixCommand;
import com.teles.yore.domain.api.YoreImage;
import com.teles.yore.domain.api.YoreRequest;

import feign.Headers;
import feign.RequestLine;

public interface YoreImageResource {

	@RequestLine("POST /image")
	@Headers("Content-Type: application/json")
	HystrixCommand<YoreImage> pixelate(YoreRequest req);
	
}
