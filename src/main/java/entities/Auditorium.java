package entities;

import java.util.Set;

/**
 * Created by macbook on 02.01.17.
 */
public class Auditorium {
    private final String name;
    private final Integer numberOfSeats;
    private final Set<Integer> vipSeats;

    public Auditorium(String name, Integer numberOfSeats, Set<Integer> vipSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public Set<Integer> getVipSeats() {
        return vipSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auditorium that = (Auditorium) o;

        if (!name.equals(that.name)) return false;
        if (!numberOfSeats.equals(that.numberOfSeats)) return false;
        return vipSeats.equals(that.vipSeats);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + numberOfSeats.hashCode();
        result = 31 * result + vipSeats.hashCode();
        return result;
    }
}
