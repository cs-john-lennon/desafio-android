package com.csjohnlennon.desafioandroid.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Repository implements Parcelable {

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("forks")
    public long forks;

    @SerializedName("stargazers_count")
    public long stargazers_count;

    @SerializedName("owner")
    public Owner owner;

    protected Repository(Parcel in) {
        name = in.readString();
        description = in.readString();
        forks = in.readLong();
        stargazers_count = in.readLong();
    }

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(forks);
        dest.writeLong(stargazers_count);
    }
}
