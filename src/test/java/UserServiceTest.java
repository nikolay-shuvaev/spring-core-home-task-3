import configuration.AppConfig;
import entities.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.UserService;

import java.time.LocalDate;

/**
 * Created by macbook on 04.01.17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest extends TestCase {
    private static final String TEST_USER_1 = "Test User 1";
    private static final String TEST_EMAIL = "test1@test.com";
    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        long id = userService.save(TEST_USER_1, LocalDate.of(1999, 11, 11), TEST_EMAIL);
        assertTrue("Id is more than 0", id > 0);
        User returnedById = userService.getById(id);
        assertEquals(TEST_USER_1, returnedById.getName());
    }

    @Test
    public void testGetUserById() {
        User userByEmail = userService.getUserByEmail(TEST_EMAIL);
        assertEquals(TEST_EMAIL, userByEmail.getEmail());
    }

    @Test
    public void testRemoveUser() {
        int initialSize = userService.getAll().size();
        long id = userService.save("Test User 2", LocalDate.of(1999, 11, 11), "test2@test.com");
        assertEquals(initialSize + 1, userService.getAll().size());
        userService.remove(id);
        assertEquals(initialSize, userService.getAll().size());
    }
}
