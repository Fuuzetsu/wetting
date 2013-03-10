package uk.co.fuuzetsu.wetting;

import java.util.*;

public class DrinkDiary {

	Map<Long, Either<Drink, Toilet>> activities;

	public DrinkDiary() {
		this.activities = new HashMap<Long, Either<Drink, Toilet>>();
	}

	public DrinkDiary(Map<Long, Either<Drink, Toilet>> m) {
		this.activities = m;
	}

	public void setActivities(Map<Long, Either<Drink, Toilet>> m) {
		this.activities = m;
	}

	public Map<Long, Either<Drink, Toilet>> getActivities() {
		return this.activities;
	}
}
