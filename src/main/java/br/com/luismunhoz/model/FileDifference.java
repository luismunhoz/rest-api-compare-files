package br.com.luismunhoz.model;

import java.util.List;

public class FileDifference {
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FileDifference [status=" + status + "]";
	}

}
