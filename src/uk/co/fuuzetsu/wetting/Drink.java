package uk.co.fuuzetsu.wetting;

public class Drink extends Event {
    protected boolean fizzy;
    public boolean isFizzy() { return fizzy; }

    public Drink(String name, boolean fizzy) {
        this(name, java.util.Calendar.getInstance().getTime(), fizzy);
    }

    public Drink(String name, java.util.Date time, boolean fizzy) {
        super(name, time);

        this.fizzy = fizzy;
    }
}
