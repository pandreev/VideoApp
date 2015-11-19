package bg.unisofia.fmi.videoapp.test;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.CourseUser;
import bg.unisofia.fmi.videoapp.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnrollUserInCourseTest {
    private User newUser;
    private Course newCourse;

    @Before
    public void setUp() throws Exception {
        newUser = new User();
        newUser.setEmail("test@test.com");
        newUser.setPassword("password");
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newCourse = new Course();
        newCourse.setCourseName("Test course");

    }

    @Test
    public void testCreateUser() throws Exception {
        CourseUser courseUser = new CourseUser(newUser, newCourse);
        newUser.addCourse(courseUser);
        assertEquals(newUser.getCourses().size(), 1);

    }
}
