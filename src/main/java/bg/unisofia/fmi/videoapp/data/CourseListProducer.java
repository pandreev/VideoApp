package bg.unisofia.fmi.videoapp.data;

import bg.unisofia.fmi.videoapp.model.Course;
import bg.unisofia.fmi.videoapp.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@RequestScoped
public class CourseListProducer {
    @Inject
    private EntityManager em;

    private List<Course> courses;

    @Produces
    @Named
    public List<Course> getCourses() {
        return courses;
    }

    public void onCourseListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Course course) {
        retrieveAllCoursesOrderedByName();
    }

    @PostConstruct
    public void retrieveAllCoursesOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteria = cb.createQuery(Course.class);
        Root<Course> courseRoot = criteria.from(Course.class);
        criteria.select(courseRoot).orderBy(cb.asc(courseRoot.get("courseName")));
        courses = em.createQuery(criteria).getResultList();
    }
}

