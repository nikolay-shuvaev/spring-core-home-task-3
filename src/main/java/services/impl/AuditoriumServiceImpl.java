package services.impl;

import dao.ScheduleTableDao;
import entities.Auditorium;
import entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.AuditoriumService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by macbook on 02.01.17
 */
@Service
public class AuditoriumServiceImpl implements AuditoriumService {
    private ScheduleTableDao scheduleTableDao;

    private List<Auditorium> auditoriumList;

    @Autowired
    public AuditoriumServiceImpl(ScheduleTableDao scheduleTableDao) {
        this.scheduleTableDao = scheduleTableDao;
    }

    @Resource(name = "auditoriumList")
    public void setAuditoriumList(List<Auditorium> auditoriumList) {
        this.auditoriumList = auditoriumList;
    }

    @Override
    public List<Auditorium> getAll() {
        return auditoriumList;
    }

    @Override
    public Auditorium getByName(String name) {
        Optional<Auditorium> auditorium = auditoriumList.stream()
                .filter(a -> a.getName().equals(name))
                .findAny();
        return auditorium.orElse(null);
    }

    @Override
    public Auditorium addEvent(Event event, Auditorium auditorium, LocalDateTime dateTime) {
        Auditorium auditoriumByEventAndDate = getAuditoriumByEventAndDate(event, dateTime);
        if (auditoriumByEventAndDate == null) {
            return scheduleTableDao.addEventToAuditorium(event, auditorium, dateTime);
        }
        return null;
    }

    @Override
    public Auditorium getAuditoriumByEventAndDate(Event event, LocalDateTime dateTime) {
        return scheduleTableDao.getAuditoriumByEventAndDate(event, dateTime);
    }
}
