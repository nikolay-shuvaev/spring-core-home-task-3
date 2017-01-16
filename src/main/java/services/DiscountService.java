package services;

import entities.Event;
import entities.User;

import java.time.LocalDateTime;

/**
 * Created by macbook on 02.01.17.
 */
public interface DiscountService {
    int getDiscount(User user, Event event, LocalDateTime dateTime, int numberOfTickets);
}
