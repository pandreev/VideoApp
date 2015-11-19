package bg.unisofia.fmi.videoapp.test;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.Video;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCourseTest {

    @Test
    public void testCreateUser() throws Exception {
        Course newCourse = new Course();
        newCourse.setCourseName("Test course");
        assertEquals(newCourse.getCourseName(), "Test course");
        newCourse.addUploadedVideo(new Video("TestVideo", "TestVideo", newCourse));
        assertEquals(newCourse.getUploadedVideos().size(), 1);

    }
}
