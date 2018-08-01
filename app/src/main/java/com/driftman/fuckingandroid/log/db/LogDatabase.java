package com.driftman.fuckingandroid.log.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.driftman.fuckingandroid.log.db.dao.LogDAO;
import com.driftman.fuckingandroid.log.entity.Log;

/**
 * Created by abk on 30/07/2018.
 */


@Database(entities = {Log.class}, version = 1)
public abstract class LogDatabase extends RoomDatabase {

    private static LogDatabase db;

    abstract public LogDAO logDAO();

    public static synchronized LogDatabase getInstance(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context, LogDatabase.class, "Log.db")
                    .build();
        }
        return db;
    }

}
