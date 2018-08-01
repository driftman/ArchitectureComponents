package com.driftman.fuckingandroid.log;

import android.content.Context;
import android.content.Intent;

import com.driftman.fuckingandroid.di.Injection;
import com.driftman.fuckingandroid.log.db.dao.LogDAO;
import com.driftman.fuckingandroid.log.entity.Log;
import com.driftman.fuckingandroid.log.network.LogAPI;

/**
 * Created by abk on 30/07/2018.
 */

public class CustomLog {

    private Context context;
    private String tag;

    public CustomLog(Context context, String tag) {
        this.context = context;
        this.tag = tag;
    }

    public void e(String content) {
        android.util.Log.e(tag, content);
        createAndSendLog(tag, Log.ERROR, content);
    }

    public void d(String content) {
        android.util.Log.d(tag, content);
        createAndSendLog(tag, Log.DEBUG, content);
    }

    public void i(String content) {
        android.util.Log.i(tag, content);
        createAndSendLog(tag, Log.INFO, content);
    }

    private void createAndSendLog(String tag, String type, String content) {
        Log log = new Log(tag, type, content);
        Intent logIntentService = new Intent(context, LogService.class);
        logIntentService.putExtra("log", log);
        context.startService(logIntentService);
    }

    public Log createLog(String tag, String type, String content) {
        return new Log(tag, type, content);
    }

}
