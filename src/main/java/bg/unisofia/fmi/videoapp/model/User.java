package bg.unisofia.fmi.videoapp.model;

import bg.unisofia.fmi.videoapp.util.Password;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Column(length = 255)
    private String password;


    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-zА-Яа-я]*", message = "must contain only letters")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-zА-Яа-я]*", message = "must contain only letters")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<CourseUser> courses;

    private Locale defaultLocale;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        this.password = password;
        this.password = Password.getSaltedHash(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.courses = new LinkedList<CourseUser>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<CourseUser> getCourses() {
        return courses;
    }

    public boolean isParticipantIn(final Course course) {
        for (CourseUser each : courses) {
            if (each.getCourse().equals(course)) {
                return true;
            }
        }
        return false;
    }


    public void addCourse(CourseUser course) {
        courses.add(course);
    }

    public void removeCourse(CourseUser course) {
        courses.remove(course);
    }

    public CourseUser getCourseUser(Course course) {
        for (CourseUser each : courses) {
            if (each.getCourse().equals(course)) {
                return each;
            }
        }
        return null;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
}