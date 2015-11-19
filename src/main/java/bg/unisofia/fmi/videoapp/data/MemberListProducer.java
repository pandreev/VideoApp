package bg.unisofia.fmi.videoapp.data;

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
public class MemberListProducer {
    @Inject
    private EntityManager em;

    private List<User> users;

    @Produces
    @Named
    public List<User> getUsers() {
        return users;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final User user) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> member = criteria.from(User.class);
        criteria.select(member).orderBy(cb.asc(member.get("username")));
        users = em.createQuery(criteria).getResultList();
    }
}
