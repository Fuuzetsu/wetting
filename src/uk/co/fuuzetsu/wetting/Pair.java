package uk.co.fuuzetsu.wetting;

public class Pair<L, R> {
	L l;
	R r;

	public Pair(L l, R r) {
		this.l = l;
		this.r = r;
	}

	public L getLeft() {
		return this.l;
	}

	public R getRight() {
		return this.r;
	}
}
