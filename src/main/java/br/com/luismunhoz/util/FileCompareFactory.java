package br.com.luismunhoz.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.luismunhoz.model.FileType;

@Component
public class FileCompareFactory {
	
	@Autowired
	BinaryFileCompare binaryFC;
	
	@Autowired
	TextFileCompare textFC;
	
	public FileCompare createFileCompare(FileType type) {
		switch (type) {
		case BINARY:
			return binaryFC;
		case TEXT:
			return textFC; 
        default: throw new IllegalArgumentException("No type for FileCompare");
		}
	}

}
