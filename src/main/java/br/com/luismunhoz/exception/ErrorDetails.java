package br.com.luismunhoz.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDetails {
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	  private LocalDateTime timestamp;
	  private String message;
	  private String details;

	  public ErrorDetails(LocalDateTime localDateTime, String message, String details) {
	    super();
	    this.timestamp = localDateTime;
	    this.message = message;
	    this.details = details;
	  }

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
