package services;

import entities.Event;
import entities.Rating;

import java.util.List;

/**
 * Created by macbook on 02.01.17.
 */
public interface EventService {
    long save(String name, double basePrice, Rating raiting);

    void remove(long id);

    Event getById(long id);

    Event getEventByName(String name);

    List<Event> getAll();
}
