package br.com.luismunhoz.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.luismunhoz.exception.UploadFileException;
import br.com.luismunhoz.model.FileDifference;
import br.com.luismunhoz.model.FileSide;
import br.com.luismunhoz.model.FileType;
import br.com.luismunhoz.util.DetectFileType;
import br.com.luismunhoz.util.FileCompare;
import br.com.luismunhoz.util.FileCompareFactory;
import br.com.luismunhoz.util.FileManager;

@Service
public class FileCompareService {
	
	@Value("${message.error.cantFindLeftFile}")
	String errorFindLeftFile;
	
	@Value("${message.error.cantFindRightFile}")
	String errorFindRightFile;
	
	@Value("${application.detectFileType}")
	Boolean tryDetectFileType;
	
	@Autowired
	FileManager fileManager;
	
	@Autowired 
	DetectFileType detectFileType;
	
	@Autowired
	FileCompareFactory fileCompareFactory;
	
	public Boolean uploadLeftFile(String id, InputStream fileContent) throws Exception {
		InputStream decodedFileContent = Base64.getMimeDecoder().wrap(fileContent);
		Boolean firstTime = !fileManager.haveFile(id, FileSide.LEFT);
		fileManager.saveFile(id, FileSide.LEFT, decodedFileContent);
		return firstTime;
	}
	
	public Boolean uploadRightFile(String id, InputStream fileContent) {
		InputStream decodedFileContent = Base64.getMimeDecoder().wrap(fileContent);
		Boolean firstTime = !fileManager.haveFile(id, FileSide.RIGHT);
		fileManager.saveFile(id, FileSide.RIGHT, decodedFileContent);
		return firstTime;
	}
	
	public FileDifference compareFiles(String id) {
		checkFiles(id);
		FileType type = detectFileType(id);
		FileCompare fileCompare = fileCompareFactory.createFileCompare(type);
		return fileCompare.compare(id);		
	}

	private void checkFiles(String id) {
		if(!fileManager.haveFile(id, FileSide.LEFT)) {
			throw new UploadFileException(errorFindLeftFile, null);
		}
		if(!fileManager.haveFile(id, FileSide.RIGHT)) {
			throw new UploadFileException(errorFindRightFile, null);
		}
	}

	private FileType detectFileType(String id) {
		FileType type = FileType.BINARY;
		if(tryDetectFileType) {
			Boolean leftType = detectFileType.isTextFile(fileManager.loadFile(id).getLeftFile());
			Boolean rightType = detectFileType.isTextFile(fileManager.loadFile(id).getRightFile());
			
			if(leftType && rightType) {
				type = FileType.TEXT;
			}
		}
		return type;
	}
}
