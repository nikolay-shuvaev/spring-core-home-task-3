import aspects.CounterAspect;
import configuration.AppConfig;
import configuration.AspectConfiguration;
import entities.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.AuditoriumService;
import services.BookingService;
import services.EventService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolai_Shuvaev on 1/11/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, AspectConfiguration.class})
public class StatisticAspectTest extends TestCase {
    private static final String TEST_EVENT_1 = "Event 1";
    private static final String TEST_EVENT_2 = "Event 2";
    private static final String TEST_EVENT_3 = "Event 3";
    private static final LocalDateTime DATE_TIME_EVENT_1 = LocalDateTime.of(2017, 11, 11, 13, 0);
    private static final LocalDateTime DATE_TIME_EVENT_2 = LocalDateTime.of(2017, 11, 11, 15, 0);
    private static final LocalDateTime DATE_TIME_EVENT_3 = LocalDateTime.of(2017, 11, 11, 17, 0);

    @Autowired
    private CounterAspect counterAspect;
    @Autowired
    private EventService eventService;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private BookingService bookingService;

    @Before
    public void init() {
        eventService.save(TEST_EVENT_1, 20, Rating.HIGH);
        eventService.save(TEST_EVENT_2, 20, Rating.HIGH);
        eventService.save(TEST_EVENT_3, 20, Rating.HIGH);
    }

    @Test
    public void testFindByNameAspect() {
        Event event1 = eventService.getEventByName(TEST_EVENT_1);
        eventService.getEventByName(TEST_EVENT_1);
        eventService.getEventByName(TEST_EVENT_1);

        Event event2 = eventService.getEventByName(TEST_EVENT_2);
        eventService.getEventByName(TEST_EVENT_2);

        Event event3 = eventService.getEventByName(TEST_EVENT_3);

        Map<Long, StatisticEntry> counters = counterAspect.getCounters();
        assertEquals(Long.valueOf(3L), counters.get(event1.getId()).getAccessedByNameCount());
        assertEquals(Long.valueOf(2L), counters.get(event2.getId()).getAccessedByNameCount());
        assertEquals(Long.valueOf(1L), counters.get(event3.getId()).getAccessedByNameCount());

        List<Auditorium> all = auditoriumService.getAll();
        auditoriumService.addEvent(event1, all.get(0), DATE_TIME_EVENT_1);
        auditoriumService.addEvent(event2, all.get(0), DATE_TIME_EVENT_2);
        auditoriumService.addEvent(event3, all.get(0), DATE_TIME_EVENT_3);


        bookingService.getTotalPrice(event1, DATE_TIME_EVENT_1, null, new HashSet<>(
                Arrays.asList(
                        Seat.of(100, SeatType.STANDARD),
                        Seat.of(101, SeatType.STANDARD))));
        bookingService.getTotalPrice(event2, DATE_TIME_EVENT_2, null, new HashSet<>(
                Arrays.asList(
                        Seat.of(100, SeatType.STANDARD),
                        Seat.of(101, SeatType.STANDARD))));
        bookingService.getTotalPrice(event3, DATE_TIME_EVENT_3, null, new HashSet<>(
                Arrays.asList(
                        Seat.of(100, SeatType.STANDARD),
                        Seat.of(101, SeatType.STANDARD))));

        bookingService.getTotalPrice(event1, DATE_TIME_EVENT_1, null, new HashSet<>(
                Arrays.asList(
                        Seat.of(102, SeatType.STANDARD),
                        Seat.of(103, SeatType.STANDARD))));
        try {
            bookingService.getTotalPrice(event2, DATE_TIME_EVENT_1, null, new HashSet<>(
                    Arrays.asList(
                            Seat.of(102, SeatType.STANDARD),
                            Seat.of(103, SeatType.STANDARD))));
            bookingService.getTotalPrice(event3, DATE_TIME_EVENT_1, null, new HashSet<>(
                    Arrays.asList(
                            Seat.of(102, SeatType.STANDARD),
                            Seat.of(103, SeatType.STANDARD))));
        } catch (Exception e) {
            // Should fail and not count
        }

        counters = counterAspect.getCounters();
        assertEquals(Long.valueOf(2L), counters.get(event1.getId()).getPriceQueriedCount());
        assertEquals(Long.valueOf(1L), counters.get(event2.getId()).getPriceQueriedCount());
        assertEquals(Long.valueOf(1L), counters.get(event3.getId()).getPriceQueriedCount());

        bookingService.bookTicket(Arrays.asList(
                Ticket.of(event1, DATE_TIME_EVENT_1, Seat.of(100, SeatType.STANDARD)),
                Ticket.of(event1, DATE_TIME_EVENT_1, Seat.of(101, SeatType.STANDARD)),
                Ticket.of(event1, DATE_TIME_EVENT_1, Seat.of(102, SeatType.STANDARD))
        ));

        bookingService.bookTicket(Arrays.asList(
                Ticket.of(event2, DATE_TIME_EVENT_2, Seat.of(100, SeatType.STANDARD)),
                Ticket.of(event2, DATE_TIME_EVENT_2, Seat.of(101, SeatType.STANDARD)),
                Ticket.of(event2, DATE_TIME_EVENT_2, Seat.of(102, SeatType.STANDARD))
        ));

        bookingService.bookTicket(Arrays.asList(
                Ticket.of(event3, DATE_TIME_EVENT_3, Seat.of(100, SeatType.STANDARD)),
                Ticket.of(event3, DATE_TIME_EVENT_3, Seat.of(101, SeatType.STANDARD)),
                Ticket.of(event3, DATE_TIME_EVENT_3, Seat.of(102, SeatType.STANDARD))
        ));

        try {
            bookingService.bookTicket(Arrays.asList(
                    Ticket.of(event1, DATE_TIME_EVENT_2, Seat.of(100, SeatType.STANDARD)),
                    Ticket.of(event1, DATE_TIME_EVENT_2, Seat.of(101, SeatType.STANDARD)),
                    Ticket.of(event1, DATE_TIME_EVENT_2, Seat.of(102, SeatType.STANDARD))
            ));
        } catch (Exception e) {
            // Should fail and not count
        }

        counters = counterAspect.getCounters();
        assertEquals(Long.valueOf(3L), counters.get(event1.getId()).getTicketBookingCount());
        assertEquals(Long.valueOf(3L), counters.get(event2.getId()).getTicketBookingCount());
        assertEquals(Long.valueOf(3L), counters.get(event3.getId()).getTicketBookingCount());
    }
}