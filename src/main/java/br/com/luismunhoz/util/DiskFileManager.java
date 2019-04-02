package br.com.luismunhoz.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import br.com.luismunhoz.exception.FileException;

public class DiskFileManager implements FileManager {
	
	@Value("${message.error.saveFile}")
	String messageErrorSaveFile;
	
	@Value("${message.error.loadFile}")
	String messageErrorLoadFile;

	@Override
	public byte[] loadFile(String path) {
			
		try {			
			File inputFile = new File(path);			
	        return FileUtils.readFileToByteArray(inputFile);
		} catch (IOException e) {
			throw new FileException(messageErrorLoadFile, e);		
		}
		
	}

	@Override
	public void saveFile(String path, byte[] fileContent) throws FileException {
		
		File outputFile = new File(path);
		try {
			FileUtils.writeByteArrayToFile(outputFile, fileContent);
		} catch (IOException e) {
			throw new FileException(messageErrorSaveFile, e);
		}
	}

	@Override
	public Boolean haveFile(String path) throws FileException {
		File inputFile = new File(path);			
		return inputFile.exists();
	}

}
