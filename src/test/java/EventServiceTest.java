import configuration.AppConfig;
import entities.Event;
import entities.Rating;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.EventService;

/**
 * Created by macbook on 04.01.17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EventServiceTest extends TestCase {
    private static final String TEST_EVENT_1 = "New Test Event 1";
    private static final String TEST_EVENT_2 = "New Test Event 2";
    private static final String TEST_EVENT_3 = "New Test Event 3";

    @Autowired
    private EventService eventService;

    @Test
    public void testRegisterEvent() {
        long id = eventService.save(TEST_EVENT_1, 20, Rating.HIGH);
        assertTrue("Id is more than 0", id > 0);
        Event returnedById = eventService.getById(id);
        assertEquals(TEST_EVENT_1, returnedById.getName());
    }

    @Test
    public void testGetEventById() {
        long id = eventService.save(TEST_EVENT_2, 20, Rating.HIGH);
        assertTrue("Id is more than 0", id > 0);
        Event eventByName = eventService.getEventByName(TEST_EVENT_2);
        assertEquals(TEST_EVENT_2, eventByName.getName());
    }

    @Test
    public void testRemoveEvent() {
        int initialSize = eventService.getAll().size();
        long id = eventService.save(TEST_EVENT_3, 40, Rating.LOW);
        assertEquals(initialSize + 1, eventService.getAll().size());
        eventService.remove(id);
        assertEquals(initialSize, eventService.getAll().size());
    }
}
