package services.impl;

import dao.EventDao;
import entities.Event;
import entities.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.EventService;

import java.util.List;

/**
 * Created by macbook on 02.01.17.
 */
@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    @Autowired
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public long save(String name, double basePrice, Rating raiting) {
        return eventDao.save(name, basePrice, raiting);
    }

    @Override
    public void remove(long id) {
        eventDao.remove(id);
    }

    @Override
    public Event getById(long id) {
        return eventDao.getById(id);
    }

    @Override
    public Event getEventByName(String name) {
        return eventDao.getEventByName(name);
    }

    @Override
    public List<Event> getAll() {
        return eventDao.getAll();
    }
}
