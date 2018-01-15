package com.akhilerm.easydialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akhil on 23/12/17.
 */

public class DialReceiver extends BroadcastReceiver {

    private static final String TAG = DialReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"Captured Outgoing Call");
        Log.e(TAG, intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
    }
}
