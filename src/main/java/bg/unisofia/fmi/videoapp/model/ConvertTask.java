package bg.unisofia.fmi.videoapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ConvertTask {

    private static int currentNumber = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int sequenceNumber;
    private String uploadDir;
    private String convertDir;
    private String snapshotDir;
    private String videoName;

    public ConvertTask(String uploadDir, String convertDir, String snapshotDir, String videoName) {
        sequenceNumber = currentNumber++;
        this.uploadDir = uploadDir;
        this.convertDir = convertDir;
        this.snapshotDir = snapshotDir;
        this.videoName = videoName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getConvertDir() {
        return convertDir;
    }

    public void setConvertDir(String convertDir) {
        this.convertDir = convertDir;
    }

    public String getSnapshotDir() {
        return snapshotDir;
    }

    public void setSnapshotDir(String snapshotDir) {
        this.snapshotDir = snapshotDir;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
