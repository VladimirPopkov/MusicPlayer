package ru.vladimir_popkov.musicplayer.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ru.vladimir_popkov.musicplayer.network.MusicAPI;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class Track implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("artist")
    private Artist mArtist;

    @SerializedName("albom")
    private String mAlbom;

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

    public String getAlbom() {
        return mAlbom;
    }

    public void setAlbom(String albom) {
        mAlbom = albom;
    }

    public String getPath() {
        return MusicAPI.ENDPOINT + "track/" + mId + "/path";
    }

    public String getCover(){
        return MusicAPI.ENDPOINT + "albom/" + mAlbom + "/cover";
    }

    @Override
    public String toString() {
        return "Track{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mArtist=" + mArtist +
                ", mAlbom='" + mAlbom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (!mId.equals(track.mId)) return false;
        if (!mName.equals(track.mName)) return false;
        if (!mArtist.equals(track.mArtist)) return false;
        return mAlbom.equals(track.mAlbom);
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mArtist.hashCode();
        result = 31 * result + mAlbom.hashCode();
        return result;
    }
}
