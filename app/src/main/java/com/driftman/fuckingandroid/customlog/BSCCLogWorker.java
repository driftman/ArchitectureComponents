package com.driftman.fuckingandroid.customlog;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BSCCLogWorker {

    private ConcurrentLinkedQueue<BSCCLogEntity> queue = new ConcurrentLinkedQueue<>();

    private String url;
    private String method;
    private Long interval;

    public BSCCLogWorker(String url, String method, Long interval) {

        this.url = url;
        this.method = method;
        this.interval = interval;

        // Preparing the worker
        BSCCWorker worker = new BSCCWorker();
        Thread workerThread = new Thread(worker);
        // Starting the worker
        workerThread.start();
    }

    private class BSCCWorker implements Runnable {

        private final String TAG = "BSCCWorker";

        @Override
        public void run() {
            long lastSync = System.currentTimeMillis();
            while(true) {
                long sinceLastSync = System.currentTimeMillis() - lastSync;
                if(queue.size() > 0 && sinceLastSync >= interval) {
                    try {
                        doSync(queue.peek());
                        lastSync = System.currentTimeMillis();
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        }

        private void doSync(BSCCLogEntity log) throws InterruptedException {
            Log.d(TAG, "Started syncing logs to our API.");
            Log.d(TAG, "HttpRequest url(" + url + "), method( " + method + ")");
            Thread.sleep(2000);
            Log.d(TAG, "Syncing logs finished successfully.");
        }
    }

    public void addToQueue(BSCCLogEntity log) {
        queue.add(log);
    }
}
