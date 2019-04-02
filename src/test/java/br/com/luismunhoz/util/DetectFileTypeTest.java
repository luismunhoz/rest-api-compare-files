package br.com.luismunhoz.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class DetectFileTypeTest {
	
	private String inputFilePathText = "texto1.txt";	
	private String inputFilePathImage = "image-test-1.jpg";

	@Test
	public void testFileTypeIsText() {
		
		ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader
          .getResource(inputFilePathText)
          .getFile());
 
        byte[] fileContent = null;
		try {
			fileContent = FileUtils.readFileToByteArray(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String encodedString = Base64
          .getEncoder()
          .encodeToString(fileContent);
        
        byte[] decodedBytes = Base64
                .getDecoder()
                .decode(encodedString);        
		
		JMimeMagicDetectFileType target = new JMimeMagicDetectFileType();
		Boolean isText = target.isTextFile(decodedBytes);
		
		assertThat(isText, is(true));
	}
	
	@Test
	public void testFileTypeIsBinary() {
		ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader
          .getResource(inputFilePathImage)
          .getFile());
 
        byte[] fileContent = null;
		try {
			fileContent = FileUtils.readFileToByteArray(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String encodedString = Base64
          .getEncoder()
          .encodeToString(fileContent);
        
        byte[] decodedBytes = Base64
                .getDecoder()
                .decode(encodedString);        
		
		JMimeMagicDetectFileType target = new JMimeMagicDetectFileType();
		Boolean isText = target.isTextFile(decodedBytes);
		
		assertThat(isText, is(false));
		
	}

}
