package com.akhilerm.easydialer;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by akhil on 15/1/18.
 */

public class CallService extends Service {

    private static DialReceiver dialReceiver;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        unregisterReceiver(dialReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startIdxit) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        dialReceiver = new DialReceiver();
        registerReceiver(dialReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
        return START_STICKY;
    }

}
