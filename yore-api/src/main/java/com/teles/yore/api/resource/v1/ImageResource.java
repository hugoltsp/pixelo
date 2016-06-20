package com.teles.yore.api.resource.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.teles.yore.domain.api.YoreImage;
import com.teles.yore.domain.api.YoreResponse;

@RestController
@RequestMapping(value = "/api/v1/image", consumes = "application/json", produces = "application/json")
public class ImageResource {

	@RequestMapping(method = RequestMethod.POST)
	public DeferredResult<ResponseEntity<YoreResponse>> pixelate(@RequestBody YoreImage yoreImage) {

		return null;
	}

}