package dao;

import entities.Auditorium;
import entities.Event;

import java.time.LocalDateTime;

/**
 * Created by NICK on 05.01.2017.
 */
public interface ScheduleTableDao {
    Auditorium addEventToAuditorium(Event event, Auditorium auditorium, LocalDateTime dateTime);

    Auditorium getAuditoriumByEventAndDate(Event event, LocalDateTime dateTime);
}
