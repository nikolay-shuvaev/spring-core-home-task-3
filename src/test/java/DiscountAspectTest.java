import aspects.DiscountAspect;
import configuration.AppConfig;
import configuration.AspectConfiguration;
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
import services.strategies.BirthdayDiscountStrategy;
import services.strategies.SoldTicketDiscountStrategy;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by NICK on 12.01.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, AspectConfiguration.class})
public class DiscountAspectTest extends TestCase {
    private static final LocalDate BIRTHDAY = LocalDate.of(2000, 11, 13);
    @Autowired
    private DiscountService discountService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private DiscountAspect discountAspect;

    private User testUser;
    private Event testEvent;

    @Before
    public void init() throws Exception {
        long userId = userService.save("Test User", BIRTHDAY, "test@test.com");
        long eventId = eventService.save("Test Event", 20.0, Rating.MID);

        testUser = userService.getById(userId);
        testEvent = eventService.getById(eventId);
    }
    @Test
    public void testTotalDiscountCount() {
        discountService.getDiscount(testUser, testEvent, BIRTHDAY.minusDays(1).atStartOfDay(), 3);
        discountService.getDiscount(testUser, testEvent, BIRTHDAY.minusDays(1).atStartOfDay(), 3);
        discountService.getDiscount(testUser, testEvent, BIRTHDAY.minusDays(1).atStartOfDay(), 3);

        discountService.getDiscount(testUser, testEvent,  BIRTHDAY.minusDays(10).atStartOfDay(), 13);
        discountService.getDiscount(null, testEvent,  BIRTHDAY.minusDays(10).atStartOfDay(), 13);
        discountService.getDiscount(null, testEvent,  BIRTHDAY.minusDays(10).atStartOfDay(), 13);

        Map<String, Long> totalDiscountApplyCounter = discountAspect.getTotalDiscountApplyCounter();
        assertEquals(Long.valueOf(3), totalDiscountApplyCounter.get(BirthdayDiscountStrategy.class.getCanonicalName()));
        assertEquals(Long.valueOf(3), totalDiscountApplyCounter.get(SoldTicketDiscountStrategy.class.getCanonicalName()));

        assertEquals(Long.valueOf(3), discountAspect.getParticularUserCount(testUser).get(BirthdayDiscountStrategy.class.getCanonicalName()));
        assertEquals(Long.valueOf(1), discountAspect.getParticularUserCount(testUser).get(SoldTicketDiscountStrategy.class.getCanonicalName()));
    }
}
