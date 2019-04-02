package br.com.luismunhoz.util;

import java.io.BufferedInputStream;

import br.com.luismunhoz.exception.FileException;

public interface FileManager {
	
	byte[] loadFile(String path);
	
	void saveFile(String path, byte[] data) throws FileException;
	
	Boolean haveFile(String path) throws FileException;

}
