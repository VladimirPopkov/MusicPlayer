package ru.vladimir_popkov.musicplayer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by v.popkov on 20.05.2018.
 */
public class Artist implements Serializable{

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("alboms")
    private List<Albom> mAlboms;

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

    public List<Albom> getAlboms() {
        return mAlboms;
    }

    public void setAlboms(List<Albom> alboms) {
        mAlboms = alboms;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mAlboms=" + mAlboms +
                '}';
    }
}
