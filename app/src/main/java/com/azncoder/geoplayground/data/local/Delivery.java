package com.azncoder.geoplayground.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aznc0der on 1/1/2018.
 */
@Entity(tableName = "Delivery")
public class Delivery implements Parcelable, Comparable<Delivery> {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "deliveryId")
    private int id;
    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;
    @SerializedName("imageUrl")
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
    @SerializedName("location")
    @ColumnInfo(name = "location")
    private Location location;

    public Delivery(@NonNull String description, String imageUrl, Location location) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return TextUtils.isEmpty(description) ? "" : description;
    }

    public String getImageUrl() {
        return TextUtils.isEmpty(imageUrl) ? "" : imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Delivery (" + getId() + ")"
                + "{" + "description = " + getDescription()
                + "imageUrl = " + getImageUrl()
                + "locations = " + getLocation().toString()
                + "}";
    }

    @Override
    public int compareTo(@NonNull Delivery delivery) {
        return this.id - delivery.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.location, flags);
    }

    protected Delivery(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<Delivery> CREATOR = new Creator<Delivery>() {
        @Override
        public Delivery createFromParcel(Parcel source) {
            return new Delivery(source);
        }

        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size];
        }
    };
}
