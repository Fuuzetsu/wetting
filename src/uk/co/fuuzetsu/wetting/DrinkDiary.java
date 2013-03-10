package uk.co.fuuzetsu.wetting;

import java.util.*;

public class DrinkDiary {

    Map<Long, Event> activities;

    public DrinkDiary() {
        this.activities = new HashMap<Long, Event>();
    }

    public DrinkDiary(Map<Long, Event> m) {
        this.activities = m;
    }

    public void setActivities(Map<Long, Event> m) {
        this.activities = m;
    }

    public Map<Long, Event> getActivities() {
        return this.activities;
    }
}
