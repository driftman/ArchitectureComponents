package com.driftman.fuckingandroid;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.driftman.fuckingandroid.di.Injection;
import com.driftman.fuckingandroid.log.CustomLog;
import com.driftman.fuckingandroid.log.db.dao.LogDAO;
import com.driftman.fuckingandroid.log.entity.Log;

/**
 * Created by abk on 01/08/2018.
 */

public class FuckingApplication extends Application {

    public static final String TAG = "FuckingApplication";

    private LogDAO logDAO;
    private CustomLog logger;

    @Override
    public void onCreate() {
        super.onCreate();
        // Setting the logger
        logger = new CustomLog(this, TAG);
        logDAO = new Injection().provideLogDAO(this);
        // Handling uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log log = logger.createLog(TAG, Log.ERROR, throwable.getMessage());
                logDAO.create(log);
            }
        });
        // Logging the activities lifecycle
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                logger.i("Activity (" + activity.getClass().getSimpleName() + ") created.");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                logger.i("Activity (" + activity.getClass().getSimpleName() + ") started.");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                logger.i("Activity (" + activity.getClass().getSimpleName() + ") resumed.");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                logger.i("Activity (" + activity.getClass().getSimpleName() + ") paused.");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                logger.i("Activity (" + activity.getClass().getSimpleName() + ") stopped.");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                logger.i("Activity (" + activity.getClass().getSimpleName() + ") destroyed.");
            }
        });
    }
}
