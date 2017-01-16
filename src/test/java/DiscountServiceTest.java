import configuration.AppConfig;
import entities.Event;
import entities.Rating;
import entities.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.DiscountService;
import services.EventService;
import services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by NICK on 05.01.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DiscountServiceTest extends TestCase {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    private User testUser;
    private Event testEvent;

    @Before
    public void init() throws Exception {
        long userId = userService.save("Test User", LocalDate.of(2000, 11, 13), "test@test.com");
        long eventId = eventService.save("Test Event", 20.0, Rating.MID);

        testUser = userService.getById(userId);
        testEvent = eventService.getById(eventId);
    }

    @Test
    public void testNoDiscountReturn() {
        int discount = discountService.getDiscount(testUser, testEvent, LocalDateTime.of(2017, 1, 1, 0, 0), 2);
        assertEquals(0, discount);
    }

    @Test
    public void testGetBirthdayDiscount() {
        int discount = discountService.getDiscount(testUser, testEvent, LocalDateTime.of(2017, 11, 12, 0, 0), 2);
        assertEquals(10, discount);
    }

    @Test
    public void testGetSoldTicketDiscount() {
        int discount = discountService.getDiscount(testUser, testEvent, LocalDateTime.of(2017, 1, 1, 0,0), 10);
        assertEquals(5, discount);
    }

    @Test
    public void testGetMoreDiscount() {
        int discount = discountService.getDiscount(testUser, testEvent, LocalDateTime.of(2017, 11, 12, 0,0), 10);
        assertEquals(10, discount);
    }

}