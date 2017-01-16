package dao.impl;

import dao.PurchasedTicketDao;
import entities.Event;
import entities.Ticket;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NICK on 06.01.2017.
 */
@Repository
public class PurchasedTicketDaoImpl implements PurchasedTicketDao {
    private Map<String, List<Ticket>> purchasedTickets = new HashMap<>();
    @Override
    public long saveTicket(Ticket ticket) {
        long id = ticket.getEvent().getId();
        LocalDateTime dateTime = ticket.getDateTime();
        String ticketId = id + "-" + dateTime;
        List<Ticket> tickets = purchasedTickets.get(ticketId);
        if (tickets == null) {
            tickets = new ArrayList<>();
            tickets.add(ticket);
            purchasedTickets.put(ticketId, tickets);
        } else {
            tickets.add(ticket);
        }
        return ticket.getEvent().getId();
    }

    @Override
    public List<Ticket> getBy(Event event, LocalDateTime dateTime) {
        return purchasedTickets.get(event.getId() + "-" + dateTime);
    }

}
