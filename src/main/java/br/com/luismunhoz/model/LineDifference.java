package br.com.luismunhoz.model;

public class LineDifference {
	
	private Integer number;
	private String leftLine;
	private String rightLine;
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getLeftLine() {
		return leftLine;
	}
	public void setLeftLine(String leftLine) {
		this.leftLine = leftLine;
	}
	public String getRightLine() {
		return rightLine;
	}
	public void setRightLine(String rightLine) {
		this.rightLine = rightLine;
	}
	@Override
	public String toString() {
		return "LineDifference [number=" + number + ", leftLine=" + leftLine + ", rightLine=" + rightLine + "]";
	}

}
