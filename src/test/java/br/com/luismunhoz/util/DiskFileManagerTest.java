package br.com.luismunhoz.util;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import br.com.luismunhoz.exception.FileException;

public class DiskFileManagerTest {
	
	private String inputFileName = "image-test-1.jpg";	
	private String outputFileName = "image-test-2.jpg";

	@Value("${application.fileLocation}")
	String fileLocation;	
	
	@Test
	public void saveFileTest() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
	        File inputFile = new File(classLoader
	        		.getResource(inputFileName)
	        		.getFile());
			byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
			String outputFilePath = inputFile.getParentFile().getAbsolutePath() + System.getProperty("file.separator") + outputFileName;
			DiskFileManager target = new DiskFileManager();
			target.saveFile(outputFilePath, fileContent);
	        File outputFile = new File(classLoader
	                .getResource(outputFileName)
	                .getFile());
	        assertTrue(FileUtils.contentEquals(inputFile, outputFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void loadFileTest() {
		ClassLoader classLoader = getClass().getClassLoader();
		String inputFilePath = classLoader
				.getResource(inputFileName)
				.getFile();					
		DiskFileManager target = new DiskFileManager();
		byte[] load = target.loadFile(inputFilePath);
		File inputFile = new File(classLoader
				.getResource(inputFileName)
				.getFile());
		assertEquals(load.length, inputFile.length());
	}
	
	@Test(expected = FileException.class)
	public void loadFileTestException() {
		ClassLoader classLoader = getClass().getClassLoader();
		DiskFileManager target = new DiskFileManager();
		byte[] load = target.loadFile("nonono");
		File inputFile = new File(classLoader
				.getResource(inputFileName)
				.getFile());
		assertEquals(load.length, inputFile.length());
	}

	@Test
	public void haveFileTest() {
		
		System.out.println(fileLocation);
		
		ClassLoader classLoader = getClass().getClassLoader();		
		String inputFilePath = classLoader
				.getResource(inputFileName)
				.getFile();			
		DiskFileManager target = new DiskFileManager();				
		assertTrue(target.haveFile(inputFilePath));
	}

}
