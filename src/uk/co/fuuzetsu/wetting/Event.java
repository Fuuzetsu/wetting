package uk.co.fuuzetsu.wetting;

import java.util.*;

public abstract class Event {
    protected String name;
    protected Date time, day;

    public Event(String name, Date time) {
        this.name = name;
        this.time = time;

        Calendar c = Calendar.getInstance();

        c.setTime(time);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        this.day = c.getTime();
    }

    public String getName() { return name; }
    public Date   getTime() { return time; }
    public Date   getDay()  { return day; }

    public static Event fromEither(Either<Drink, Toilet> edt) {
        return edt.isLeft() ? edt.getLeft() : edt.getRight();
    }
}
