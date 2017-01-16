package dao.impl;

import dao.EventDao;
import entities.Event;
import entities.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by macbook on 03.01.17
 */
@Repository
public class EventDaoImpl implements EventDao {
    private long currentId = 1;
    private Map<Long, Event> events = new HashMap<>();

    @Override
    public long save(String name, double basePrice, Rating rating) {
        Event event = new Event(currentId++, name, basePrice, rating);
        long id = event.getId();
        events.put(id, event);
        return id;
    }

    @Override
    public void remove(long id) {
        events.remove(id);
    }

    @Override
    public Event getById(long id) {
        return events.get(id);
    }

    @Override
    public Event getEventByName(String name) {
        for (Event event : events.values()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> getAll() {
        return new ArrayList<>(events.values());
    }
}
