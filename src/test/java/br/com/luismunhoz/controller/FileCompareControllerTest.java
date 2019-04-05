package br.com.luismunhoz.controller;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.jni.File;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.luismunhoz.CompareFilesApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CompareFilesApplication.class})
@AutoConfigureMockMvc
public class FileCompareControllerTest {
	
	@Autowired
	private MockMvc mvc;

	
	private static final String RESOURCE_URL = "/v1/diff";

	@Test
	@Ignore
	public void test() {
		
		JsonObject requestJson;
		JsonParser jsonParser = new JsonParser();
		requestJson = (JsonObject)jsonParser.parse("{ \"fileContent\": \"RmlzcnQgdGVzdA==\" }");
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(requestJson.toString());
		
		ResponseEntity<JsonNode> response = testRestTemplate.exchange(RESOURCE_URL+"/test_01/left", HttpMethod.POST, request, JsonNode.class);
		
		System.out.println(response.getStatusCode());
		
	}
	
	@Test
	public void test2() throws Exception {
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("image-test-1.jpg.base64.txt");
		
		//System.out.println("{ \"fileContent\": \" "+IOUtils.toString(inputStream)+"\" }");
			
		
		String respLeft = mvc.perform(MockMvcRequestBuilders.post(RESOURCE_URL+"/test_01/left")
				.content("{ \"fileContent\": \" "+IOUtils.toString(inputStream)+"\" }")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andReturn().getResponse().getContentAsString();	
		
		System.out.println(respLeft);		
		
		String respRight = mvc.perform(MockMvcRequestBuilders.post(RESOURCE_URL+"/test_01/right")
				.content("{ \"fileContent\": \"RmlzcnQgdGVzdA==\" }")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andReturn().getResponse().getContentAsString();	
		
		System.out.println(respRight);
		
		String respCompare = mvc.perform(MockMvcRequestBuilders.get(RESOURCE_URL+"/test_01")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andReturn().getResponse().getContentAsString();	
		
		System.out.println(respCompare);
		
		
	}

}
