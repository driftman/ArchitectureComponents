package com.driftman.fuckingandroid.customlog;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;

public class BSCCLogService extends Service {

    private BSCCLogWorker worker;

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentName componentName = new ComponentName(this, BSCCLogService.class);
        try {
            Bundle bundle = getPackageManager()
                    .getServiceInfo(componentName, PackageManager.GET_META_DATA).metaData;
            config(bundle);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("An error happened while gathering meta data.");
        }
    }

    private void config(Bundle bundle) {
        if(!bundle.containsKey("url"))
            throw new RuntimeException("<meta-data android:name=\"url\" android:value=\"your-url\"/>");
        if(!bundle.containsKey("method"))
            throw new RuntimeException("<meta-data android:name=\"method\" android:value=\"your-method\"/>");
        if(!bundle.containsKey("worker_interval_ms"))
            throw new RuntimeException("<meta-data android:name=\"worker_interval_ms\" android:value=\"your-interval\"/>");
        String url = bundle.getString("url");
        String method = bundle.getString("method");
        Long interval = bundle.getLong("worker_interval_ms");
        // Setting up the worker
        worker = new BSCCLogWorker(url, method, interval);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BSCCLogEntity log = intent.getParcelableExtra("log");
        worker.addToQueue(log);
        return START_STICKY;
    }

        @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
