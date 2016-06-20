package com.teles.yore.api.resource.v1;

import java.io.IOException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.teles.yore.api.util.Pixelator;
import com.teles.yore.domain.api.YoreImage;
import com.teles.yore.domain.api.YoreRequest;
import com.teles.yore.domain.api.YoreResponse;

@RestController
@RequestMapping(value = "/api/v1/image", consumes = "application/json", produces = "application/json")
public class ImageResource {

	private static final Logger logger = LoggerFactory.getLogger(ImageResource.class);

	@RequestMapping(method = RequestMethod.POST)
	public DeferredResult<ResponseEntity<YoreResponse>> pixelate(@RequestBody YoreRequest yoreRequest) {
		YoreImage yoreImage = yoreRequest.getYoreImage();
		int pixelSize = yoreRequest.getPixelSize();
		DeferredResult<ResponseEntity<YoreResponse>> result = new DeferredResult<>();

		logger.debug("Pixelating image: {}, size: {}", yoreImage.getName(), yoreImage.getSize());

		try {

			byte[] pixelatedImage = Pixelator.pixelate(yoreImage.getImage(), pixelSize);
			YoreImage pixelatedYoreImage = new YoreImage();
			pixelatedYoreImage.setImage(pixelatedImage);
			pixelatedYoreImage.setName(pixelatedImageName(yoreImage.getName()));
			YoreResponse response = new YoreResponse();
			response.setMessage("Ok");
			response.setYoreImage(pixelatedYoreImage);
			result.setResult(new ResponseEntity<>(response, HttpStatus.OK));

		} catch (IOException e) {
			YoreResponse response = new YoreResponse();
			response.setMessage("Error");
			result.setResult(new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR));
			logger.error("Error: ", e);
		}

		return result;
	}

	private final static String pixelatedImageName(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(Instant.now().toEpochMilli()).append("_").append("yore").append(name);
		return sb.toString();
	}

}