package services;

import entities.Event;
import entities.Seat;
import entities.Ticket;
import entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by macbook on 02.01.17
 */
public interface BookingService {
    double getTotalPrice(Event event, LocalDateTime dateTime, User user, Set<Seat> seats);

    boolean bookTicket(List<Ticket> tickets);

    List<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime);

    List<Ticket> getPurchasedTicketsForUser(User user);
}
