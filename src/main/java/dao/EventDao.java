package dao;

import entities.Event;
import entities.Rating;

import java.util.List;

/**
 * Created by macbook on 03.01.17.
 */
public interface EventDao {
    long save(String name, double basePrice, Rating rating);

    void remove(long id);

    Event getById(long id);

    Event getEventByName(String name);

    List<Event> getAll();
}
