package ru.vladimir_popkov.musicplayer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by v.popkov on 20.05.2018.
 */
public class Artist implements Serializable{

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    public String getId() {
        return mId;
    }

    public void setmId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
