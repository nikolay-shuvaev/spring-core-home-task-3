package dao;

import entities.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by NICK on 06.01.2017
 */
public interface PurchasedTicketDao {
    long saveTicket(Ticket ticket);

    List<Ticket> getBy(Event event, LocalDateTime dateTime);

    List<Ticket> getBy(User user);

    Set<Seat> getOccupiedSeats(Event event, LocalDateTime dateTime);
}
