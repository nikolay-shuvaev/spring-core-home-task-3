package entities;

/**
 * Created by macbook on 02.01.17.
 */
public class Event {

    private final long id;
    private final String name;
    private final double basePrice;
    private final Rating rating;

    public Event(long id, String name, double basePrice, Rating rating) {

        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Rating getRating() {
        return rating;
    }
}
