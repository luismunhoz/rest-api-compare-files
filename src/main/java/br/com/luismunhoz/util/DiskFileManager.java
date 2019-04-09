package br.com.luismunhoz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileSide;
import br.com.luismunhoz.model.FilesToCompare;

@Component
public class DiskFileManager implements FileManager {
	
	@Value("${application.fileLocation}")
	String path;
	
	@Value("${message.error.saveFile}")
	String messageErrorSaveFile;
	
	@Value("${message.error.saveRightFile}")
	String messageErrorSaveRightFile;
	
	@Value("${message.error.loadFile}")
	String messageErrorLoadFile;
	
	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);		

	@Override
	public FilesToCompare loadFile(String id) {
		FilesToCompare response = new FilesToCompare();			
		try {
			response.setLeftFile(new FileInputStream(getFullPath(id, FileSide.LEFT)));
			response.setRightFile(new FileInputStream(getFullPath(id, FileSide.RIGHT)));
		} catch (IOException e) {
			throw new FileException(messageErrorLoadFile, e);		
		}
		logger.debug("FilesToCompare - File loaded.");
		return response;
	}

	@Override
	public void saveFile(String id, FileSide side, InputStream fileContent) throws FileException {		
		String fullPath = getFullPath(id, side); 
        File targetFile = new File(fullPath);
        try {
			FileUtils.copyInputStreamToFile(fileContent, targetFile);
		} catch (IOException e) {
			throw new FileException(messageErrorSaveFile, e);
		}
		logger.debug("FilesToCompare - File saved.");
	}

	@Override
	public Boolean haveFile(String id, FileSide side) throws FileException {
		String fullPath = getFullPath(id, side);
		try {
			File checkFile = new File(fullPath);			
			return checkFile.exists();			
		} catch (Exception e) {
			throw new FileException(messageErrorLoadFile, e);
		}
	}
	
	private String getFullPath(String id, FileSide side) {
		String fullPath;
		if(side==FileSide.LEFT) {
			fullPath = path + System.getProperty("file.separator") +"left_" + id;
		}else {
			fullPath = path + System.getProperty("file.separator") +"right_" + id;
		}
		return fullPath;
	}
	
	

}
