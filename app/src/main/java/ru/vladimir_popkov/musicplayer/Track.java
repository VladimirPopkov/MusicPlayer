package ru.vladimir_popkov.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class Track implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCover);
        parcel.writeString(mPath);
        parcel.writeString(mTrackName);
        parcel.writeString(mTrackPerformer);
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        // распаковываем объект из Parcel
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    private Track(Parcel parcel) {
        mCover = parcel.readString();
        mPath = parcel.readString();
        mTrackName = parcel.readString();
        mTrackPerformer = parcel.readString();
    }
}
