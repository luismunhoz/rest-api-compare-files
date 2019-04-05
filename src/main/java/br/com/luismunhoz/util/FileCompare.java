package br.com.luismunhoz.util;

import br.com.luismunhoz.exception.FileException;
import br.com.luismunhoz.model.FileDifference;

public abstract class FileCompare {

	public abstract FileDifference compare(String id) throws FileException;

}
