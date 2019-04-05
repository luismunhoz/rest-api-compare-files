package br.com.luismunhoz.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.model.FileType;

@Component
public class FileCompareFactory {
	
	@Autowired
	BinaryFileCompare binaryFC;
	
	@Autowired
	TextFileCompare textFC;
	
	private static final Log logger = LogFactory.getLog(BinaryFileCompare.class);			
	
	public FileCompare createFileCompare(FileType type) {
		switch (type) {
		case BINARY:
			logger.debug("BinaryFileCompare created");
			return binaryFC;
		case TEXT:
			logger.debug("TextFileCompare created");
			return textFC; 
        default: throw new IllegalArgumentException("No type for FileCompare");
		}
	}

}
