package com.csjohnlennon.desafioandroid.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pull implements Parcelable{

    @SerializedName("title")
    public String title;
    @SerializedName("body")
    public String body;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("html_url")
    public String html_url;
    @SerializedName("user")
    public Owner owner;

    protected Pull(Parcel in) {
        title = in.readString();
        body = in.readString();
        created_at = in.readString();
        html_url = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<Pull> CREATOR = new Creator<Pull>() {
        @Override
        public Pull createFromParcel(Parcel in) {
            return new Pull(in);
        }

        @Override
        public Pull[] newArray(int size) {
            return new Pull[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(created_at);
        dest.writeString(html_url);
        dest.writeParcelable(owner, flags);
    }
}
