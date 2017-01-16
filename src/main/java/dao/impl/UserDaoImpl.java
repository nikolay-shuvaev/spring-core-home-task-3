package dao.impl;

import dao.UserDao;
import entities.Ticket;
import entities.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by macbook on 03.01.17
 */
@Repository
public class UserDaoImpl implements UserDao {
    private long currentId = 1;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public long save(String name, LocalDate birthday, String email) {
        User user = new User(currentId++, name, birthday, email);
        Long id = user.getId();
        users.put(id, user);
        return id;
    }

    @Override
    public void remove(long id) {
        users.remove(id);
    }

    @Override
    public User getById(long id) {
        return users.get(id);
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void addPurchasedTicket(User user, Ticket ticket) {
        User storedUser = users.get(user.getId());
        storedUser.getTickets().add(ticket);
    }
}
