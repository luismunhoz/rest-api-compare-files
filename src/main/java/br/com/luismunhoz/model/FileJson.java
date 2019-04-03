package br.com.luismunhoz.model;

import javax.validation.constraints.Size;

public class FileJson {
	
	@Size(max=1048576)
	private String fileContent;

	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	@Override
	public String toString() {
		return "File [fileContent=" + fileContent + "]";
	}

}
