package bg.unisofia.fmi.videoapp.controller;


import bg.unisofia.fmi.videoapp.model.Video;
import bg.unisofia.fmi.videoapp.model.WatchingUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoObject {
    private Long id;

    private String videoName;

    private String originalName;

    private String subtitles;
    private String alternativeUrl;

    private Date creationDate;

    private final List<WatchedUser> watches;

    public VideoObject(final Video video) {

        this.id = video.getId();
        this.videoName = video.getVideoName();
        this.originalName = video.getOriginalName();
        this.subtitles = video.getSubtitles();
        this.alternativeUrl = video.getAlternativeUrl();
        this.creationDate = video.getCreationDate();
        watches = new ArrayList<WatchedUser>();
        for (final WatchingUser user : video.getWatchingUsers()) {
            watches.add(new WatchedUser(user));
        }
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

    public String getOriginalName() {
        return originalName;
    }

    public String getAlternativeUrl() {
        return alternativeUrl;
    }

    public void setAlternativeUrl(String alternativeUrl) {
        this.alternativeUrl = alternativeUrl;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public List<WatchedUser> getWatches() {
        return watches;
    }

    public String getFormattedCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        return sdf.format(creationDate);
    }

    public static class WatchedUser {
        private final String name;
        private final String startTime;
        private final String endTime;

        public WatchedUser(final WatchingUser user) {
            this.name = user.getUser();
            this.startTime = calculateTime(user.getStartTime());
            this.endTime = calculateTime(user.getEndTime());
        }

        public String getName() {
            return name;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        private String calculateTime(final int totalSeconds) {
            final int hours = totalSeconds / 3600;
            final int remain = totalSeconds - hours * 3600;
            final int minutes = remain / 60;
            final int seconds = remain - minutes * 60;
            return String.valueOf(hours) + "ч. " + minutes + "м. " + seconds + "с.";
        }
    }


}
