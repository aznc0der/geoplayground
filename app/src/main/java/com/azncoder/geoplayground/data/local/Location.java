package com.azncoder.geoplayground.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aznc0der on 1/1/2018.
 */

public class Location implements Parcelable {
    @SerializedName("lat")
    @ColumnInfo(name = "lat")
    private float lat;
    @SerializedName("lng")
    @ColumnInfo(name = "lng")
    private float lng;
    @SerializedName("address")
    @ColumnInfo(name = "address")
    private String address;

    public Location(float lat, float lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    @Override
    public String toString() {
        return "Location{" + "lat = " + lat
                + "lng = " + lng
                + "address = " + getAddress()
                + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.lat);
        dest.writeFloat(this.lng);
        dest.writeString(this.address);
    }

    protected Location(Parcel in) {
        this.lat = in.readFloat();
        this.lng = in.readFloat();
        this.address = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
