package com.teles.pixelo.app.tests;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.teles.pixelo.api.client.PixeloClient;
import com.teles.pixelo.app.endpoint.v1.PixeloEndpoint;
import com.teles.pixelo.domain.PixeloImage;
import com.teles.pixelo.domain.PixeloRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class })
@WebAppConfiguration
public class PixeloTests {

	private static final String API_V1 = "/app/upload";

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		PixeloClient client = new PixeloClient(mockEnv());
		this.mockMvc = standaloneSetup(new PixeloEndpoint(client)).build();
	}

	@Test
	public void unsupported_http_methods_test() throws Exception {
		this.mockMvc.perform(get(API_V1).accept(MULTIPART_FORM_DATA_VALUE)).andExpect(status().is4xxClientError());
		this.mockMvc.perform(put(API_V1).accept(MULTIPART_FORM_DATA_VALUE)).andExpect(status().is4xxClientError());
		this.mockMvc.perform(delete(API_V1).accept(MULTIPART_FORM_DATA_VALUE)).andExpect(status().is4xxClientError());
	}

	@Test
	public void supported_http_method_and_invalid_request_test() throws Exception {
		this.mockMvc.perform(post(API_V1).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(""))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@Ignore
	//FIXME
	public void supported_http_method_and_valid_request_integration_test() throws Exception {

		PixeloRequest mockRequest = createRequest();

		MockMultipartFile file = new MockMultipartFile(mockRequest.getPixeloImage().getName(), mockRequest.getPixeloImage().getImage()); 
		
		this.mockMvc.perform(fileUpload(API_V1).file(file).contentType(MULTIPART_FORM_DATA)).andExpect(status().isOk());
	}

	private static PixeloRequest createRequest() throws IOException {
		PixeloRequest request = new PixeloRequest();
		PixeloImage image = new PixeloImage();
		image.setName("photo.jpg");
		image.setImage(Files.readAllBytes(Paths.get("../photo.jpg")));
		request.setPixelSize(6);
		request.setPixeloImage(image);
		return request;
	}

	private static MockEnvironment mockEnv() {
		MockEnvironment env = new MockEnvironment();
		env.setProperty("pixelo.api.url", "http://localhost:9090/api/v1");
		env.setProperty("pixelo.client.request.timeout", "2000");
		return env;
	}
}