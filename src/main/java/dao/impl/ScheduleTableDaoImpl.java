package dao.impl;

import dao.ScheduleTableDao;
import entities.Auditorium;
import entities.Event;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NICK on 05.01.2017.
 */
@Repository
public class ScheduleTableDaoImpl implements ScheduleTableDao {
    private Map<String, Auditorium> scheduleTable = new HashMap<>();

    @Override
    public Auditorium addEventToAuditorium(Event event, Auditorium auditorium, LocalDateTime dateTime) {
        scheduleTable.put(event.getId() + "-" + dateTime, auditorium);
        return auditorium;
    }

    @Override
    public Auditorium getAuditoriumByEventAndDate(Event event, LocalDateTime dateTime) {
        return scheduleTable.get(event.getId() + "-" + dateTime);
    }
}
