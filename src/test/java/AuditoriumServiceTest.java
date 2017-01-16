import configuration.AppConfig;
import entities.Auditorium;
import entities.Event;
import entities.Rating;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.AuditoriumService;
import services.EventService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by NICK on 05.01.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AuditoriumServiceTest extends TestCase {
    public static final String TEST_EVENT_NAME = "Test Event";
    public static final String TEST_AUDITORIUM_NAME = "Center Name 1";
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private EventService eventService;

    @Test
    public void testGetAllAuditoriums() {
        List<Auditorium> auditoriums = auditoriumService.getAll();
        assertTrue(auditoriums.size() > 0);
    }

    @Test
    public void testGetByName() {
        List<Auditorium> auditoriums = auditoriumService.getAll();
        Auditorium auditorium = auditoriums.get(0);
        Auditorium auditoriumByName = auditoriumService.getByName(auditorium.getName());
        assertEquals(auditorium.getName(), auditoriumByName.getName());
    }

    @Test
    public void testAddEventToAuditorium() {
        long eventId = eventService.save(TEST_EVENT_NAME, 30.0, Rating.MID);
        Event event = eventService.getById(eventId);
        Auditorium auditorium = auditoriumService.getByName(TEST_AUDITORIUM_NAME);
        Auditorium result = auditoriumService.addEvent(event, auditorium, LocalDateTime.of(2017, 11, 11, 13, 0));
        assertEquals(auditorium.getName(), result.getName());
        Auditorium alreadyAdded = auditoriumService.addEvent(event, auditorium, LocalDateTime.of(2017, 11, 11, 13, 0));
        assertEquals(null, alreadyAdded);
    }
}
