package com.teles.yore.app.tests;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teles.yore.api.client.YoreClient;
import com.teles.yore.app.endpoint.v1.YoreEndpoint;
import com.teles.yore.domain.YoreImage;
import com.teles.yore.domain.YoreRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class })
@WebAppConfiguration
public class YoreTests {

	private static final String API_V1 = "/app/upload";

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		YoreClient client = new YoreClient(mockEnv());
		this.mockMvc = standaloneSetup(new YoreEndpoint(client)).build();
	}

	@Test
	public void unsupported_http_methods_test() throws Exception {
		this.mockMvc.perform(get(API_V1).accept(MULTIPART_FORM_DATA_VALUE)).andExpect(status().is4xxClientError());
		this.mockMvc.perform(put(API_V1).accept(MULTIPART_FORM_DATA_VALUE)).andExpect(status().is4xxClientError());
		this.mockMvc.perform(delete(API_V1).accept(MULTIPART_FORM_DATA_VALUE)).andExpect(status().is4xxClientError());
	}

	@Test
	@Ignore
	public void supported_http_method_and_invalid_request_test() throws Exception {
		this.mockMvc.perform(post(API_V1).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Ignore
	public void supported_http_method_and_valid_request_integration_test() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(createRequest());

		this.mockMvc.perform(post(API_V1).accept(MULTIPART_FORM_DATA_VALUE).contentType(MULTIPART_FORM_DATA_VALUE).content(json))
				.andExpect(status().isOk());
	}

	private static YoreRequest createRequest() throws IOException {
		YoreRequest request = new YoreRequest();
		YoreImage yoreImage = new YoreImage();
		yoreImage.setName("photo.jpg");
		yoreImage.setImage(Files.readAllBytes(Paths.get("../photo.jpg")));
		request.setPixelSize(6);
		request.setYoreImage(yoreImage);
		return request;
	}

	private static MockEnvironment mockEnv() {
		MockEnvironment env = new MockEnvironment();
		env.setProperty("yore.api.url", "http://localhost:9090/api/v1");
		env.setProperty("yore.client.request.timeout", "2000");
		return env;
	}
}