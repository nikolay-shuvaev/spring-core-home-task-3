import aspects.LuckyWinnerAspect;
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
import services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static entities.SeatType.STANDARD;

/**
 * Created by NICK on 13.01.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, AspectConfiguration.class})
public class LotteryAspectTest extends TestCase {
    private static final String TEST_AUDITORIUM = "Center Name 4";
    private static final LocalDateTime EVENT_DATE_TIME = LocalDateTime.of(2017, 2, 2, 13, 0);

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private BookingService bookingService;

    private User testUser;
    private Event testEvent;

    @Before
    public void init() {
        long userId = userService.save("Test User", LocalDate.of(2000, 11, 11), "test@test.com");
        long eventId = eventService.save("Test Event", 20.0, Rating.MID);

        testUser = userService.getById(userId);
        testEvent = eventService.getById(eventId);

        Auditorium auditorium = auditoriumService.getByName(TEST_AUDITORIUM);
        auditoriumService.addEvent(testEvent, auditorium, EVENT_DATE_TIME);
    }

    @Test
    public void testLuckyTickets() {
        luckyWinnerAspect.setLotteryService(new LotteryService() {
            @Override
            public boolean isYouLucky() {
                return true;
            }
        });

        double totalPrice = bookingService.getTotalPrice(testEvent, EVENT_DATE_TIME, testUser,
                new HashSet<>(Arrays.asList(Seat.of(11, STANDARD), Seat.of(12, STANDARD), Seat.of(13, STANDARD))));

        assertEquals(0., totalPrice);
    }

    @Test
    public void testNoLuckyTickets() {
        luckyWinnerAspect.setLotteryService(new LotteryService() {
            @Override
            public boolean isYouLucky() {
                return false;
            }
        });

        double totalPrice = bookingService.getTotalPrice(testEvent, EVENT_DATE_TIME, testUser,
                new HashSet<>(Arrays.asList(Seat.of(11, STANDARD), Seat.of(12, STANDARD), Seat.of(13, STANDARD))));

        assertEquals(60., totalPrice);
    }
}
