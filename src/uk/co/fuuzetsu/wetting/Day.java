package uk.co.fuuzetsu.wetting;

import java.util.*;

public class Day {
    private Boolean wet;
	private Date start;
    private Map<Date, Either<Drink, Toilet>> events;

	public Day(Boolean wet, Date start, Map<Date, Either<Drink, Toilet>> events) {
		this.wet = wet;
		this.start = start;
		this.events = events;
	}
}
