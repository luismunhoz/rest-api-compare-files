package br.com.luismunhoz.exception;

public class FileTypeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FileTypeException(String message, Exception e) {
		super(message, e);
	}

}
