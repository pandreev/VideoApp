package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.User;
import bg.unisofia.fmi.videoapp.service.CourseService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class CourseRegistration {
    @Inject
    private FacesContext facesContext;

    @Inject
    private CourseService courseService;

    private Course newCourse;

    @Produces
    @Named
    public Course getNewCourse() {
        return newCourse;
    }

    public void create() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            Authentication auth = (Authentication)externalContext.getSessionMap().get("auth");
            final User creator = auth.getLoggedUser();
            final Long courseId = courseService.create(newCourse, creator);
            initNewCourse();

            externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + courseId);

        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Create error"));
        }
    }



    @PostConstruct
    public void initNewCourse() {
        newCourse = new Course();
    }
}
