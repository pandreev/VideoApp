package bg.unisofia.fmi.videoapp.test;

import bg.unisofia.fmi.videoapp.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WatchVideoTest {

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
        newCourse.addUploadedVideo(new Video("TestVideo", "TestVideo", newCourse));
        CourseUser courseUser = new CourseUser(newUser, newCourse);
        newUser.addCourse(courseUser);
    }

    @Test
    public void testCreateUser() throws Exception {
        Video video = newCourse.getUploadedVideos().iterator().next();
        video.addWatchingUser(new WatchingUser(newUser.getLastName(), 0, 30));
        assertEquals(video.getWatchingUsers().size(), 1);
        assertEquals(video.getWatchingUsers().iterator().next().getUser(), newUser.getLastName());
        assertEquals(video.getWatchingUsers().iterator().next().getStartTime(), 0);
        assertEquals(video.getWatchingUsers().iterator().next().getEndTime(), 30);

    }
}
