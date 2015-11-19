package bg.unisofia.fmi.videoapp.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
public class Video {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    private String videoName;

    private String originalName;

    @ManyToOne
    @JoinColumn(name = "subs_id")
    private Subtitles subtitles;

    private String alternativeUrl;

    @NotNull
    private Date creationDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<WatchingUser> watchingUsers;

    public Video() {
        watchingUsers = new HashSet<WatchingUser>();
    }

    public Video(final String originalName, final String videoName, final Course course) {
        this.originalName = originalName;
        this.videoName = videoName;
        this.course = course;
        this.creationDate = new Date();
        watchingUsers = new HashSet<WatchingUser>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getSubtitles() {
        return subtitles == null ? null : subtitles.getFileName();
    }

    public void setSubtitles(Subtitles subtitles) {
        this.subtitles = subtitles;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getAlternativeUrl() {
        return alternativeUrl;
    }

    public void setAlternativeUrl(String alternativeUrl) {
        this.alternativeUrl = alternativeUrl;
    }

    public String getFormattedCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        return sdf.format(creationDate);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<WatchingUser> getWatchingUsers() {
        return watchingUsers;
    }

    public void addWatchingUser(final WatchingUser user) {
        watchingUsers.add(user);
    }
}
