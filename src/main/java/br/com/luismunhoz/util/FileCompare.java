package br.com.luismunhoz.util;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileDifference;

public interface FileCompare {
	
	public FileDifference compare(String id) throws FileException;

}
