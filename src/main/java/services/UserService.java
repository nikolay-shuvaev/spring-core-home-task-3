package services;

import entities.Ticket;
import entities.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by macbook on 02.01.17.
 */
public interface UserService {

    long save(String name, LocalDate birthday, String email);

    void remove(long id);

    User getById(long id);

    User getUserByEmail(String email);

    List<User> getAll();

    void addPurchasedTicket(User user, Ticket ticket);
}
