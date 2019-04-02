package br.com.luismunhoz.model;

import java.util.List;

public class BinaryFileDifference extends FileDifference {

	private List<Integer> diffs;

	public List<Integer> getDiffs() {
		return diffs;
	}

	public void setDiffs(List<Integer> diffs) {
		this.diffs = diffs;
	}

	@Override
	public String toString() {
 		return "BinaryFileDifference [size=" + super.getSize() + "diffs=" + diffs + "]";
	}

}
