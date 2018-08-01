package com.driftman.fuckingandroid.customlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class BSCCLog {

    private Context context;
    private String tag;

    public BSCCLog(Context context, String tag) {
        this.context = context;
        this.tag = tag;
    }

    public void e(String log) {
        prepareAndSendLog("error", log);
    }

    public void d(String log) {
        prepareAndSendLog("debug", log);
    }

    public void i(String log) {
        prepareAndSendLog("info", log);
    }

    public void v(String log) {
        prepareAndSendLog("verbose", log);
    }

    private void prepareAndSendLog(String type, String message) {
        BSCCLogEntity log = new BSCCLogEntity(tag, type, message);
        launchService(log);
    }

    private void launchService(BSCCLogEntity log) {
        Intent logService = new Intent(context, BSCCLogService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("log", log);
        logService.putExtras(bundle);
        context.startService(logService);
    }

}