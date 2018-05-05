package ru.vladimir_popkov.musicplayer;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class Track {

    private String mTrackPerformer;
    private String mTrackName;
    private String mCover;
    private String mPath;

    public Track(String trackPerformer, String trackName, String cover, String path) {
        mTrackPerformer = trackPerformer;
        mTrackName = trackName;
        mCover = cover;
        mPath = path;
    }

    public String getTrackPerformer() {
        return mTrackPerformer;
    }

    public void setTrackPerformer(String mTrackPerformer) {
        this.mTrackPerformer = mTrackPerformer;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String mName) {
        this.mTrackName = mName;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String mCover) {
        this.mCover = mCover;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }
}
