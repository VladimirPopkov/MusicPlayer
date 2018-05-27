package ru.vladimir_popkov.musicplayer.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ru.vladimir_popkov.musicplayer.network.MusicAPI;

/**
 * Created by v.popkov on 20.05.2018.
 */
public class Albom implements Serializable{

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("artist")
    private Artist mArtist;

    @SerializedName("yaer")
    private String mYear;

    @SerializedName("tracks")
    private List<Track> mTracks;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Artist getArtist() {
        return mArtist;
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getCover() {
        return MusicAPI.ENDPOINT + "albom/" + mId + "/cover";
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    @Override
    public String toString() {
        return "Albom{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mArtist=" + mArtist +
                ", mYear='" + mYear + '\'' +
                ", mTracks=" + mTracks +
                '}';
    }
}
