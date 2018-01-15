package com.akhilerm.easydialer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by akhil on 23/12/17.
 */

public class CallDetectService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not supporting binding
        return null;
    }
}
