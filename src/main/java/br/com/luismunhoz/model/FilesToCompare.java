package br.com.luismunhoz.model;

import java.io.InputStream;

public class FilesToCompare {

	private InputStream leftFile;
	private InputStream rightFile;
	
	public InputStream getLeftFile() {
		return leftFile;
	}
	public void setLeftFile(InputStream leftFile) {
		this.leftFile = leftFile;
	}
	public InputStream getRightFile() {
		return rightFile;
	}
	public void setRightFile(InputStream rightFile) {
		this.rightFile = rightFile;
	}
	@Override
	public String toString() {
		return "FilesToCompare [leftFile=" + leftFile + ", rightFile=" + rightFile + "]";
	}
	
}
