package br.com.luismunhoz.util;

import java.io.BufferedInputStream;

import br.com.luismunhoz.exception.FileException;

public interface FileManager {
	
	BufferedInputStream loadFile(String path);
	
	void saveFile(String path, byte[] data, String extension) throws FileException;
	
	Boolean haveFile(String path) throws FileException;

}
