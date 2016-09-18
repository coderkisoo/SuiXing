package com.vehicle.suixing.suixing.bean.MapBean;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps.model.LatLng;

/**
 * Created by KiSoo on 2016/7/24.
 */
public class MapInfo implements Parcelable{
    private LatLng latLng;
    private String address;

    public MapInfo(LatLng latLng, String address) {
        this.latLng = latLng;
        this.address = address;
    }

    protected MapInfo(Parcel in) {
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        address = in.readString();
    }

    public static final Creator<MapInfo> CREATOR = new Creator<MapInfo>() {
        @Override
        public MapInfo createFromParcel(Parcel in) {
            return new MapInfo(in);
        }

        @Override
        public MapInfo[] newArray(int size) {
            return new MapInfo[size];
        }
    };

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
        dest.writeString(address);
    }
}
