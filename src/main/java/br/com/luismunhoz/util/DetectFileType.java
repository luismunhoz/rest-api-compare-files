package br.com.luismunhoz.util;

import java.io.InputStream;

import br.com.luismunhoz.exception.FileTypeException;

public interface DetectFileType {
	
	public Boolean isTextFile(InputStream fileContent) throws FileTypeException;

}
