package com.driftman.fuckingandroid.log.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by abk on 30/07/2018.
 */

@Entity(tableName = "Logs")
public class Log implements Parcelable {

    public static final String INFO = "INFO";
    public static final String DEBUG = "DEBUG";
    public static final String ERROR = "ERROR";

    @PrimaryKey
    @ColumnInfo()
    private transient Long id;
    @ColumnInfo()
    private transient String tag;
    @ColumnInfo()
    private String type;
    @ColumnInfo()
    private String message;

    public Log(String tag, String type, String message) {
        this.tag = tag;
        this.type = type;
        this.message = message;
    }

    protected Log(Parcel in) {
        tag = in.readString();
        type = in.readString();
        message = in.readString();
    }

    public static final Creator<Log> CREATOR = new Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel in) {
            return new Log(in);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setMessage(String content) {
        this.message = content;
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
