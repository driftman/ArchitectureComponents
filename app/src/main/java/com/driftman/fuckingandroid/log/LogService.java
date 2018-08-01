package com.driftman.fuckingandroid.log;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.driftman.fuckingandroid.di.Injection;
import com.driftman.fuckingandroid.log.db.dao.LogDAO;
import com.driftman.fuckingandroid.log.entity.Log;
import com.driftman.fuckingandroid.log.network.LogAPI;

/**
 * Created by abk on 30/07/2018.
 */

public class LogService extends Service {

    private LogWorker logWorker;

    private LogAPI logAPI;
    private LogDAO logDAO;

    @Override
    public void onCreate() {
        super.onCreate();
        Bundle bundle = null;
        ComponentName logService = new ComponentName(this, LogService.class);
        try {
            bundle = getPackageManager().getServiceInfo(logService, PackageManager.GET_META_DATA)
                    .metaData;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException();
        }
        // Configuring the log service with provided metadata
        config(bundle);
    }

    private void config(Bundle bundle) {

        if(!bundle.containsKey("token"))
            throw new RuntimeException("Please provide <meta-data name=\"token\" value=\"your-app-token\"/> in </service>");

        String token = bundle.getString("token");

        logAPI = new Injection().provideLogAPI();
        logDAO = new Injection().provideLogDAO(this);

        logWorker = new LogWorker(logDAO, logAPI);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            Log log = intent.getParcelableExtra("log");
            logWorker.addToQueue(log);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
