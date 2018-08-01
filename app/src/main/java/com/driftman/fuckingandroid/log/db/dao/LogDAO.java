package com.driftman.fuckingandroid.log.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.driftman.fuckingandroid.log.entity.Log;

import java.util.List;

/**
 * Created by abk on 30/07/2018.
 */

@Dao
public interface LogDAO {

    @Query("SELECT * FROM Logs;")
    List<Log> read();

    @Query("SELECT COUNT(*) FROM Logs;")
    Long count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Log log);

    @Query("DELETE FROM Logs;")
    void delete();

}
