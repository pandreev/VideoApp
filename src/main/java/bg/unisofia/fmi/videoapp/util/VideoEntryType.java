package bg.unisofia.fmi.videoapp.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VideoEntryType",
        propOrder = {"videoName",
                "videoId"})
public class VideoEntryType {

    @XmlElement(name = "videoName", required = true)
    private String videoName;
    @XmlElement(name = "videoId", required = true)
    private String videoId;

    public VideoEntryType() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
