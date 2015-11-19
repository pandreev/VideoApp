package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.User;
import bg.unisofia.fmi.videoapp.service.CourseService;
import bg.unisofia.fmi.videoapp.service.UserService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model
public class AllCourses {

    @Inject
    private FacesContext facesContext;

    @Inject
    private CourseService courseService;

    @Inject
    private UserService userService;

    private List<CourseBean> allCourses;

    public List<CourseBean> getAllCourses() {
        ExternalContext externalContext = facesContext.getExternalContext();
        Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
        final User loggedUser = auth.getLoggedUser();
        List<Course> courses = courseService.getAllCourses();
        allCourses = new ArrayList<CourseBean>();
        for (Course course : courses) {
            allCourses.add(new CourseBean(course, loggedUser.isParticipantIn(course)));
        }
        return allCourses;
    }

    public void enrollCourse() {
        try {
            ExternalContext externalContext = facesContext.getExternalContext();
            String courseId = externalContext.getRequestParameterMap().get("courseId");
            Course course = courseService.findCourse(Long.parseLong(courseId));
            Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
            final User loggedIn = auth.getLoggedUser();
            userService.enrollCourse(loggedIn, course);
            changeEnrolment(course, true);
            externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + course.getId());
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Enrollment error"));
        }
    }

    public void withdrawCourse() {
        try {
            ExternalContext externalContext = facesContext.getExternalContext();
            String courseId = externalContext.getRequestParameterMap().get("courseId");
            Course course = courseService.findCourse(Long.parseLong(courseId));
            Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
            final User loggedIn = auth.getLoggedUser();
            userService.withdrawCourse(loggedIn, course);
            changeEnrolment(course, false);
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.jsf");
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Withdraw error"));
        }
    }

    private void changeEnrolment(final Course course, final boolean status) {
        for (final CourseBean each : allCourses) {
            if (each.getCourse().equals(course)) {
                each.setEnrolled(status);
                return;
            }
        }
    }

    public static class CourseBean {
        private Course course;
        private boolean isEnrolled;

        public CourseBean(Course course, boolean enrolled) {
            this.course = course;
            isEnrolled = enrolled;
        }

        public Course getCourse() {
            return course;
        }

        public boolean isEnrolled() {
            return isEnrolled;
        }

        public void setEnrolled(boolean enrolled) {
            isEnrolled = enrolled;
        }
    }
}
