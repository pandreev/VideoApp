package bg.unisofia.fmi.videoapp.test;

import bg.unisofia.fmi.videoapp.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateUserTest {

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setEmail("test@test.com");
        newUser.setPassword("password");
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        assertEquals(newUser.getFirstName(), "Test");

    }
}
