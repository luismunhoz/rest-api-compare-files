package br.com.luismunhoz.util;

import br.com.luismunhoz.exception.FileTypeException;

public interface DetectFileType {
	
	public Boolean isTextFile(byte[] fileContent) throws FileTypeException;

}
