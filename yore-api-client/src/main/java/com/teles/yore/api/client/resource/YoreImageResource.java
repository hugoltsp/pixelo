package com.teles.yore.api.client.resource;

import com.teles.yore.domain.api.YoreRequest;
import com.teles.yore.domain.api.YoreResponse;

import feign.RequestLine;

public interface YoreImageResource {

	@RequestLine("POST /image")
	YoreResponse pixelate(YoreRequest req);
	
}
