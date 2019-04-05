package br.com.luismunhoz.model;

import java.util.List;

public class BinaryFileDifference extends FileDifference {

	private List<DiffOffSet> diffs;

	public List<DiffOffSet> getDiffs() {
		return diffs;
	}

	public void setDiffs(List<DiffOffSet> diffs) {
		this.diffs = diffs;
	}

	@Override
	public String toString() {
		return "BinaryFileDifference [status=" + super.getStatus() + "diffs=" + diffs + "]";
	}

}
