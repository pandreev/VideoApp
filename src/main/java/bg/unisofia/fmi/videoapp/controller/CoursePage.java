package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.CourseUser;
import bg.unisofia.fmi.videoapp.model.User;
import bg.unisofia.fmi.videoapp.model.Video;
import bg.unisofia.fmi.videoapp.service.CourseService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Model
@ViewScoped
public class CoursePage {

    @Inject
    private FacesContext facesContext;

    @Inject
    private EntityManager em;

    @Inject
    private CourseService courseService;

    private Course course;

    private String newVideoName;
    private String altUrl;
    private Long editId;

    @PostConstruct
    public void initCourse() {
        ExternalContext externalContext = facesContext.getExternalContext();
        String courseId = externalContext.getRequestParameterMap().get("courseId");
        if (courseId == null) {
            courseId = externalContext.getRequestParameterMap().get("course:courseId");
        }
        course = em.find(Course.class, Long.parseLong(courseId));
    }

    @Produces
    @Named
    public Course getCourse() {
        return course;
    }

    public Long getEditId() {
        return editId;
    }

    public void setEditId(Long editId) {
        this.editId = editId;
    }

    public List<VideoObject> getUploadedVideos() {
        String resourceDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources");
        final String fileSeparator = System.getProperty("file.separator");
        final String convertDir = resourceDir + fileSeparator + "video" + fileSeparator;
        final List<VideoObject> uploadedVideos = new LinkedList<VideoObject>();
        final Set<Video> videos = new HashSet<Video>(course.getUploadedVideos());
        final List<Video> sortedVideos = new ArrayList<Video>(videos);
        Collections.sort(sortedVideos, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        for (Video video : sortedVideos) {
            if (new File(convertDir + video.getVideoName() + "_high.mp4").exists()) {
                uploadedVideos.add(new VideoObject(video));
            }
        }
        return uploadedVideos;
    }

    public String getNewVideoName() {
        return newVideoName;
    }

    public void setNewVideoName(String newVideoName) {
        if (!"".equals(newVideoName)) {
            this.newVideoName = newVideoName;
        }
    }

    public String getAltUrl() {
        return altUrl;
    }

    public void setAltUrl(String altUrl) {
        if (!"".equals(altUrl)) {
            this.altUrl = altUrl;
        }
    }

    public void enrollUser() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
            final User creator = auth.getLoggedUser();
            CourseUser courseUser = new CourseUser(creator, course);
            creator.addCourse(courseUser);

        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Create error"));
        }
    }

    public void saveVideoWatched() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
        final User user = auth.getLoggedUser();
        final String videoId = externalContext.getRequestParameterMap().get("course:videoTimeId");
        final String start = externalContext.getRequestParameterMap().get("course:videoStartTime");
        final int startTime = start == null ? 0 : start.contains(".") ? Integer.valueOf(start.substring(0, start.indexOf("."))) : Integer.valueOf(start);
        final String end = externalContext.getRequestParameterMap().get("course:videoEndTime");
        final int endTime = end == null ? 0 : end.contains(".") ? Integer.valueOf(end.substring(0, end.indexOf("."))) : Integer.valueOf(end);
        courseService.saveVideoWatched(user, videoId, startTime, endTime);
        externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + course.getId());

    }

    public void removeVideo(final Long id) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        courseService.removeVideo(course.getId(), id);
        externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + course.getId());
    }

    public void setVideoName(final Long id) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        courseService.setVideoName(id, newVideoName);
        externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + course.getId());

    }

    public void setAlternativeUrl(final Long id) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        courseService.addAlternativeUrl(id, altUrl);
        externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + course.getId());

    }

    public boolean getIsCourseAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
        final User user = auth.getLoggedUser();
        return user.getCourseUser(course) != null && user.getCourseUser(course).isAdmin();
    }
}
