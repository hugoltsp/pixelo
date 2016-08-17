package com.teles.pixelo.app.endpoint.v1;

import java.util.concurrent.Future;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.netflix.hystrix.HystrixCommand;
import com.teles.pixelo.api.client.PixeloClient;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

@RestController
@RequestMapping(value = "/app/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PixeloEndpoint {

	private static final Logger log = LoggerFactory.getLogger(PixeloEndpoint.class);

	private final PixeloClient client;

	@Inject
	public PixeloEndpoint(PixeloClient client) {
		this.client = client;
	}

	@RequestMapping(method = RequestMethod.POST)
	public DeferredResult<ResponseEntity<?>> pixelate(@RequestParam("file") MultipartFile file,
			@RequestParam("pixelSize") int pixelSize) {
		DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

		try {

			PixeloRequest request = new PixeloRequest();
			request.setPixelSize(pixelSize);
			PixeloImage image = new PixeloImage();
			request.setPixeloImage(image);
			image.setName(file.getOriginalFilename());
			image.setImage(file.getBytes());

			HystrixCommand<PixeloImage> hystrixCommand = this.client.pixelate(request);
			Future<PixeloImage> future = hystrixCommand.queue();
			PixeloImage pixeloImage = future.get();

			result.setResult(new ResponseEntity<>(pixeloImage, HttpStatus.OK));
		} catch (Exception e) {
			log.error("Error", e);
			result.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.contentType(MediaType.APPLICATION_JSON).build());
		}

		return result;
	}

}
