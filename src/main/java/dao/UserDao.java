package dao;

import entities.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by macbook on 03.01.17.
 */
public interface UserDao {
    long save(String name, LocalDate birthday, String email);

    void remove(long id);

    User getById(long id);

    User getUserByEmail(String email);

    List<User> getAll();
}
