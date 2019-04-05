package br.com.luismunhoz.exception;

public class UploadFileException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UploadFileException(String message, Exception e) {
		super(message, e);
	}

}
