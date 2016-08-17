package com.teles.pixelo.api.client.resource;

import com.netflix.hystrix.HystrixCommand;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

import feign.Headers;
import feign.RequestLine;

public interface PixeloImageResource {

	@RequestLine("POST /image")
	@Headers("Content-Type: application/json")
	HystrixCommand<PixeloImage> pixelate(PixeloRequest req);
	
}
