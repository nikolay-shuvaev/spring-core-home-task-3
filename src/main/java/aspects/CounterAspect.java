package aspects;

import dao.CounterDao;
import entities.Event;
import entities.StatisticEntry;
import entities.Ticket;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Nikolai_Shuvaev on 1/11/2017
 */
@Aspect
@Component
public class CounterAspect {
    private CounterDao counterDao;

    @Pointcut("execution(* services.EventService.getEventByName(..))")
    public void eventNameAccess() {

    }

    @Pointcut("execution(* services.BookingService.getTotalPrice(..))")
    public void eventPriceAccess() {

    }

    @Pointcut("execution(* services.BookingService.bookTicket(..))")
    public void eventBooking() {

    }

    @AfterReturning(pointcut = "eventNameAccess()",
            returning = "event")
    public void countEventByNameAccess(Event event) {
        counterDao.incrementAccessedByName(event.getId());
    }

    @AfterReturning("eventPriceAccess() && args(event, ..)")
    public void countEventPriceAccess(Event event) {
        counterDao.incrementPriceQueried(event.getId());
    }

    @AfterReturning("eventBooking() && args(tickets, ..)")
    public void countEventBooking(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            counterDao.incrementTicketBooking(ticket.getEvent().getId());
        }
    }

    public Map<Long, StatisticEntry> getCounters() {
        return counterDao.getAllByEventId();
    }

    @Autowired
    public void setCounterDao(CounterDao counterDao) {
        this.counterDao = counterDao;
    }
}
