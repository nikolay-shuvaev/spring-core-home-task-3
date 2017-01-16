package entities;

import java.time.LocalDateTime;

/**
 * Created by macbook on 02.01.17.
 */
public class Ticket {
    private User user;
    private Event event;
    private LocalDateTime dateTime;
    private Seat seat;

    private Ticket(User user, Event event, LocalDateTime dateTime, Seat seat) {
        this.user = user;
        this.event = event;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Seat getSeat() {
        return seat;
    }

    public static Ticket of(Event event, LocalDateTime dateTime, Seat seat) {
        return new Ticket(null, event, dateTime, seat);
    }

    public Ticket add(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
