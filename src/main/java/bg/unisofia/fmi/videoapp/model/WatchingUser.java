package bg.unisofia.fmi.videoapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class WatchingUser {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String user;

    private int startTime;

    private int endTime;

    public WatchingUser() {
    }

    public WatchingUser(final String user, final int startTime, final int endTime) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUser() {
        return user;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

}
