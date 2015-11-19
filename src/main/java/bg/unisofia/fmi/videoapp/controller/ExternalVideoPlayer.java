package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.Video;
import bg.unisofia.fmi.videoapp.service.CourseService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Model
@ViewScoped
public class ExternalVideoPlayer {

    @Inject
    private EntityManager em;

    @Inject
    private CourseService courseService;

    private Video video;

    @PostConstruct
    public void initCourse() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String videoId = externalContext.getRequestParameterMap().get("videoId");
        if (videoId == null) {
            videoId = externalContext.getRequestParameterMap().get("extVideo:videoId");

        }
        video = em.find(Video.class, Long.parseLong(videoId));
    }

    public VideoObject getVideoToPlay() {
        return new VideoObject(video);
    }
}

