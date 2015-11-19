package bg.unisofia.fmi.videoapp.util;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RootType", propOrder = {"videoEntry"})
@XmlRootElement(name = "videos")
public class VideoRootType {

    @XmlElement(name = "videoEntry")
    private List<VideoEntryType> videoEntry;

    public List<VideoEntryType> getList() {
        if (videoEntry == null) {
            videoEntry = new ArrayList<VideoEntryType>();
        }
        return this.videoEntry;
    }
}
