package dao;

import entities.Event;
import entities.Ticket;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by NICK on 06.01.2017.
 */
public interface PurchasedTicketDao {
    long saveTicket(Ticket ticket);

    List<Ticket> getBy(Event event, LocalDateTime dateTime);
}
