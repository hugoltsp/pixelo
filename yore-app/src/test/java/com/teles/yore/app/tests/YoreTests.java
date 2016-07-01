package com.teles.yore.app.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.teles.yore.app.endpoint.v1.YoreEndpoint;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockServletContext.class })
@WebAppConfiguration
public class YoreTests {

	private static final String API_V1 = "/app/upload";

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = standaloneSetup(new YoreEndpoint()).build();
	}

	@Test
	public void unsupported_http_methods_test() throws Exception {
		this.mockMvc.perform(get(API_V1).accept(APPLICATION_JSON)).andExpect(status().is4xxClientError());
		this.mockMvc.perform(put(API_V1).accept(APPLICATION_JSON)).andExpect(status().is4xxClientError());
		this.mockMvc.perform(delete(API_V1).accept(APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void supported_http_method_and_invalid_request_test() throws Exception {
		this.mockMvc.perform(post(API_V1).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest());
	}
}