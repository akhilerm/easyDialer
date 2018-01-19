package com.akhilerm.easydialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        if(!intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER).equals("800505"))
            setResultData();
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:800505")));
    }
}
