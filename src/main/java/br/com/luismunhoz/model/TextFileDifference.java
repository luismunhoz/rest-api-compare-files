package br.com.luismunhoz.model;

import java.util.List;

public class TextFileDifference extends FileDifference {
	
	private List<LineDifference> lines;

	public List<LineDifference> getLines() {
		return lines;
	}

	public void setLines(List<LineDifference> lines) {
		this.lines = lines;
	}

	@Override
	public String toString() {
		return "TextFileDifference [status=" + super.getStatus() + "lines=" + lines + "]";
	}

}
