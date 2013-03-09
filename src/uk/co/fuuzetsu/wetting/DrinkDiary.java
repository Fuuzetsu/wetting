package uk.co.fuuzetsu.wetting;

import java.util.*;

public class DrinkDiary {

	Map<Date, String> = activities;

	public DrinkDiary() {
		this.activities = new HashMap<Date, String>();
	}

	public DrinkDiary(Map<Date, String> m) {
		this.activities = m;
	}

	public void setActivities(Map<Date, String> m) {
		this.activities = m;
	}

	public Map<Date, String> getActivities() {
		return this.activities;
	}
}
