package com.akhilerm.easydialer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by akhil on 15/1/18.
 */

public class CallService extends Service {

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
