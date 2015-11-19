package bg.unisofia.fmi.videoapp.service;

import bg.unisofia.fmi.videoapp.model.*;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class CourseService {
    @Inject
    private EntityManager em;


    @Inject
    private Event<Course> courseEventSrc;

    public Course findCourse(final Long id) {
        return em.find(Course.class, id);
    }

    public Video findVideo(final Long id) {
        return em.find(Video.class, id);
    }

    public List<Course> getAllCourses() {
        final List<Course> allCourses = em.createQuery("select c from Course c").getResultList();
        return allCourses;
    }

    public Long create(final Course course, final User creator) throws Exception {

        em.persist(course);
        CourseUser courseUser = new CourseUser(creator, course);
        courseUser.setAdmin(true);
        em.persist(courseUser);

        User user = em.find(User.class, creator.getEmail());
        user.addCourse(courseUser);
        courseEventSrc.fire(course);
        return course.getId();
    }

    public void addVideo(final String originalName, final String courseId, final String fileName) {
        Course course = findCourse(Long.parseLong(courseId));
        Video video = new Video(originalName, fileName, course);
        em.persist(video);
        course.addUploadedVideo(video);
    }

    public void removeVideo(final Long courseId, final Long videoId) {
        Course course = findCourse(courseId);
        Video video = findVideo(videoId);
        course.removeUploadedVideo(video);
        em.remove(video);
    }

    public void setVideoName(final Long videoId, final String newName) {
        Video video = findVideo(videoId);
        video.setOriginalName(newName);
    }

    public void addSubtitles(final Long videoId, final String fileName) {
        Video video = findVideo(videoId);
        Subtitles subtitles = new Subtitles(fileName);
        em.persist(subtitles);
        video.setSubtitles(subtitles);
    }

    public void addAlternativeUrl(final Long videoId, final String alternativeUrl) {
        Video video = findVideo(videoId);
        video.setAlternativeUrl(alternativeUrl);
    }

    public void addVideoTask(final String fileName, final String uploadedDir, final String convertDir, final String snapshotDir) {
        ConvertTask convertTask = new ConvertTask(uploadedDir, convertDir, snapshotDir, fileName);
        em.persist(convertTask);
    }

    public void saveVideoWatched(final User user, final String videoId, final int startTime, final int endTime) {
        Video video = findVideo(Long.parseLong(videoId));
        String userName = user.getFirstName() + " " + user.getLastName();
        WatchingUser watchingUser = new WatchingUser(userName, startTime, endTime);
        em.persist(watchingUser);
        video.addWatchingUser(watchingUser);
    }
}


