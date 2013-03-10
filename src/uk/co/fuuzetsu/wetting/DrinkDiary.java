package uk.co.fuuzetsu.wetting;

import java.util.*;

public class DrinkDiary {

	Map<Date, Either<Drink, Toilet>> activities;

	public DrinkDiary() {
		this.activities = new HashMap<Date, Either<Drink, Toilet>>();
	}

	public DrinkDiary(Map<Date, Either<Drink, Toilet>> m) {
		this.activities = m;
	}

	public void setActivities(Map<Date, Either<Drink, Toilet>> m) {
		this.activities = m;
	}

	public Map<Date, Either<Drink, Toilet>> getActivities() {
		return this.activities;
	}
}
