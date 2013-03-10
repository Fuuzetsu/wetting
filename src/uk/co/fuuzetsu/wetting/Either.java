package uk.co.fuuzetsu.wetting;

public class Either<L, R> {
	L left;
	R right;
	Boolean isLeft;


	/* We use the useless tag to prevent type erasure */
	public Either(L left, Boolean tag) {
		this.left = left;
		isLeft = true;
	}

	public Either(R right) {
		this.right = right;
		isLeft = false;
	}

	public Boolean isLeft() {
		return this.isLeft;
	}

	public Boolean isRight() {
		return !this.isLeft;
	}

	public R getRight() {
		return this.right;
	}

	public L getLeft() {
		return this.left;
	}
}
