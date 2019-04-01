package br.com.luismunhoz.model;

public class File {
	
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
