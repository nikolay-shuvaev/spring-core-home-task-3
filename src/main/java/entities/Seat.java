package entities;

/**
 * Created by macbook on 02.01.17.
 */
public class Seat {
    private final int seatNumber;
    private final SeatType type;

    private Seat(int seatNumber, SeatType type) {
        this.seatNumber = seatNumber;
        this.type = type;
    }

    public SeatType getType() {
        return type;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public static Seat of(int seatNumber, SeatType type) {
        return new Seat(seatNumber, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (seatNumber != seat.seatNumber) return false;
        return type == seat.type;
    }

    @Override
    public int hashCode() {
        int result = seatNumber;
        result = 31 * result + type.hashCode();
        return result;
    }
}
