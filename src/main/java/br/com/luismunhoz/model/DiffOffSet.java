package br.com.luismunhoz.model;

public class DiffOffSet {
	
	private long offset;
	private long length;
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "DiffOffSet [offset=" + offset + ", length=" + length + "]";
	}
}
