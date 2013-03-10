package uk.co.fuuzetsu.wetting;

public class Drink {
	Boolean fizzy;
	String name;

	public Drink(String name, Boolean fizzy) {
		this.fizzy = fizzy;
		this.name = name;
	}

	public Boolean isFizzy() {
		return this.fizzy;
	}

	public String getName() {
		return this.name;
	}

}
