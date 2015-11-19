package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.Video;
import bg.unisofia.fmi.videoapp.service.CourseService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.io.InputStream;

@Model
@ViewScoped
public class FileDownload {

    @Inject
    private EntityManager em;

    @Inject
    private CourseService courseService;

    public StreamedContent getFile(final Long id) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        Video video = courseService.findVideo(id);
        InputStream stream = ((ServletContext) externalContext.getContext()).getResourceAsStream("/resources/video/" + video.getVideoName() + "_high.mp4");
        return new DefaultStreamedContent(stream, "video/mp4", video.getVideoName());
    }
}
