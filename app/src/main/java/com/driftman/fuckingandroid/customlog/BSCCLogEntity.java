package com.driftman.fuckingandroid.customlog;

import android.os.Parcel;
import android.os.Parcelable;

public class BSCCLogEntity implements Parcelable {

    private String tag;
    private String type;
    private String message;

    public BSCCLogEntity(String tag, String type, String message) {
        this.tag = tag;
        this.type = type;
        this.message = message;
    }

    protected BSCCLogEntity(Parcel in) {
        tag = in.readString();
        type = in.readString();
        message = in.readString();
    }

    public static final Creator<BSCCLogEntity> CREATOR = new Creator<BSCCLogEntity>() {
        @Override
        public BSCCLogEntity createFromParcel(Parcel in) {
            return new BSCCLogEntity(in);
        }

        @Override
        public BSCCLogEntity[] newArray(int size) {
            return new BSCCLogEntity[size];
        }
    };

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tag);
        parcel.writeString(type);
        parcel.writeString(message);
    }
}
