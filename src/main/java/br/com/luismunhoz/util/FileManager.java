package br.com.luismunhoz.util;

import java.io.InputStream;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileSide;
import br.com.luismunhoz.model.FilesToCompare;

public interface FileManager {

	FilesToCompare loadFile(String id);

	void saveFile(String id, FileSide side, InputStream fileContent) throws FileException;

	Boolean haveFile(String id, FileSide side) throws FileException;

}
