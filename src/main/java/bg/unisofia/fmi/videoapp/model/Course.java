package bg.unisofia.fmi.videoapp.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
public class Course {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    private String courseName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    private List<Video> uploadedVideos;

    public Course() {
        this.uploadedVideos = new ArrayList<Video>();
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Video> getUploadedVideos() {
        return uploadedVideos;
    }

    public void addUploadedVideo(Video uploadedVideo) {
        uploadedVideos.add(uploadedVideo);
    }

    public void removeUploadedVideo(Video uploadedVideo) {
        uploadedVideos.remove(uploadedVideo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != null ? !id.equals(course.id) : course.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
