package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.service.CourseService;
import bg.unisofia.fmi.videoapp.util.ConverterScheduler;
import org.primefaces.event.FileUploadEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.*;

@ManagedBean(name = "upload")
@ViewScoped
public class FileUpload {

    @Inject
    private CourseService courseService;

    @Inject
    private ConverterScheduler convertorScheduler;

    public void upload(FileUploadEvent event) throws IOException {
        FacesMessage msg;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String fileName = event.getFile().getFileName();
        final String courseId = externalContext.getRequestParameterMap().get("course:courseId");
        final String newFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf('.'));
        try {
            copyFile(newFileName, event.getFile().getInputstream());
            msg = new FacesMessage("Success! ", fileName + " is uploaded.");
        } catch (IOException e) {
            msg = new FacesMessage("Fail! ", fileName + " is not uploaded.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        courseService.addVideo(fileName.substring(0, fileName.lastIndexOf('.')), courseId, newFileName.substring(0, newFileName.lastIndexOf('.')));
        externalContext.redirect(externalContext.getRequestContextPath() + "/coursePage.jsf?courseId=" + courseId);
    }

    public void uploadSub(FileUploadEvent event) {
        FacesMessage msg;
        Long videoId = (Long) event.getComponent().getAttributes().get("videoId");
        String fileName = event.getFile().getFileName();
        final String newFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf('.'));
        try {
            copySubFile(newFileName, event.getFile().getInputstream());
            msg = new FacesMessage("Success! ", fileName + " is uploaded.");
        } catch (IOException e) {
            msg = new FacesMessage("Fail! ", fileName + " is not uploaded.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        courseService.addSubtitles(videoId, newFileName);

    }

    public void copyFile(final String fileName, final InputStream in) throws IOException {
        final String resourceDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources");
        final String fileSeparator = System.getProperty("file.separator");
        final String uploadedDir = resourceDir + fileSeparator + "uploaded" + fileSeparator;
        final String convertDir = resourceDir + fileSeparator + "video" + fileSeparator;
        final String snapshotDir = resourceDir + fileSeparator + "snapshot" + fileSeparator;
        OutputStream out = new FileOutputStream(new File(uploadedDir + fileName));
        int read;
        byte[] bytes = new byte[1024];

        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        in.close();
        out.flush();
        out.close();
        courseService.addVideoTask(fileName, uploadedDir, convertDir, snapshotDir);
    }

    public void copySubFile(final String fileName, final InputStream in) throws IOException {
        final String resourceDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources");
        final String fileSeparator = System.getProperty("file.separator");
        final String uploadedDir = resourceDir + fileSeparator + "subtitles" + fileSeparator;
        OutputStream out = new FileOutputStream(new File(uploadedDir + fileName));
        int read;
        byte[] bytes = new byte[1024];

        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        in.close();
        out.flush();
        out.close();
    }
}
