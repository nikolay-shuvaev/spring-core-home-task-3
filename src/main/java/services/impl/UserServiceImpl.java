package services.impl;

import dao.PurchasedTicketDao;
import dao.UserDao;
import entities.Ticket;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.UserService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by macbook on 02.01.17.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private PurchasedTicketDao purchasedTicketDao;

    @Override
    public long save(String name, LocalDate birthday, String email) {
        return userDao.save(name, birthday, email);
    }

    @Override
    public void remove(long id) {
        userDao.remove(id);
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void addPurchasedTicket(User user, Ticket ticket) {
//        purchasedTicketDao.addPurchasedTicket(user, ticket);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPurchasedTicketDao(PurchasedTicketDao purchasedTicketDao) {
        this.purchasedTicketDao = purchasedTicketDao;
    }
}
