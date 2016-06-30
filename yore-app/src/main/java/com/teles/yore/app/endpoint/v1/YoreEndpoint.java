package com.teles.yore.app.endpoint.v1;

import java.util.concurrent.Future;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.netflix.hystrix.HystrixCommand;
import com.teles.yore.api.client.YoreClient;
import com.teles.yore.domain.YoreImage;
import com.teles.yore.domain.YoreRequest;

@RestController
@RequestMapping(value = "/app/upload", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class YoreEndpoint {

	private static final Logger log = LoggerFactory.getLogger(YoreEndpoint.class);

	@Inject
	private YoreClient client;

	@RequestMapping(method = RequestMethod.POST)
	public DeferredResult<ResponseEntity<?>> pixelate(@RequestBody YoreRequest request) {
		DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

		try {

			HystrixCommand<YoreImage> hystrixCommand = this.client.pixelate(request);
			Future<YoreImage> future = hystrixCommand.queue();
			YoreImage yoreImage = future.get();
			result.setResult(ResponseEntity.status(HttpStatus.OK).contentLength(yoreImage.getSize())
					.contentType(MediaType.APPLICATION_JSON).body(yoreImage));

		} catch (Exception e) {
			log.error("Error", e);
			result.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.contentType(MediaType.APPLICATION_JSON).build());
		}

		return result;
	}

}
