package bg.unisofia.fmi.videoapp.service;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.CourseUser;
import bg.unisofia.fmi.videoapp.model.User;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Locale;

@Stateless
public class UserService {
    @Inject
    private EntityManager em;

    @Inject
    private Event<User> memberEventSrc;

    public User findUser(final String email) {
        return em.find(User.class, email);
    }

    public void register(final User user) throws Exception {
        em.persist(user);
        memberEventSrc.fire(user);
    }

    public void setDefaultLocale(final User loggedUser, final Locale locale) {
        User user = findUser(loggedUser.getEmail());
        user.setDefaultLocale(locale);
    }

    public void enrollCourse(final User enrolledUser, final Course course) {
        User user = findUser(enrolledUser.getEmail());
        CourseUser courseUser = new CourseUser(user, course);
        em.persist(courseUser);
        user.addCourse(courseUser);
    }

    public void withdrawCourse(final User enrolledUser, final Course course) {
        User user = findUser(enrolledUser.getEmail());
        CourseUser courseUser = user.getCourseUser(course);
        user.removeCourse(courseUser);
        em.remove(courseUser);
    }


}

