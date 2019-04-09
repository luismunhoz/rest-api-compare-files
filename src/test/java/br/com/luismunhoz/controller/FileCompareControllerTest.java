package br.com.luismunhoz.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.luismunhoz.CompareFilesApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CompareFilesApplication.class})
@AutoConfigureMockMvc
public class FileCompareControllerTest {
	
	@Autowired
	private MockMvc mvc;

	
	private static final String RESOURCE_URL = "/v1/diff";
	
	@Test
	public void testCompareFiles() throws Exception {
				
		ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader
          .getResource("shakespeare_b64.txt")
          .getFile());
        
        String data = new String ( Files.readAllBytes(inputFile.toPath()));
				
		String respLeft = mvc.perform(MockMvcRequestBuilders.post(RESOURCE_URL+"/test_01/left")
				.content("{ \"fileContent\": \" "+data+"\" }")
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
	
    @Test
    public void testCompareLargeFiles() throws Exception {
    	
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("image-test-1.jpg.base64.txt");

        MockMultipartFile firstFile = new MockMultipartFile("file", inputStream);

        String respLeft = mvc.perform(MockMvcRequestBuilders.multipart(RESOURCE_URL+"/bigfile/test_02/left")
        		.file(firstFile)
        		.param("some-random", "4"))
        		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        		.andReturn().getResponse().getContentAsString();
        System.out.println(respLeft);
        
        String respRight = mvc.perform(MockMvcRequestBuilders.multipart(RESOURCE_URL+"/bigfile/test_02/right")
        		.file(firstFile)
        		.param("some-random", "4"))
        		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        		.andReturn().getResponse().getContentAsString();
        System.out.println(respRight);
        
		String respCompare = mvc.perform(MockMvcRequestBuilders.get(RESOURCE_URL+"/test_02")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andReturn().getResponse().getContentAsString();	
        System.out.println(respCompare);
    }	

}
