package com.akhilerm.easydialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akhil on 23/12/17.
 * Captures the outgoing call and redirects it
 */

public class DialReceiver extends BroadcastReceiver {

    private static final String TAG = DialReceiver.class.getName();

    private int toCountry;
    private DialerSettings dialerSettings = new DialerSettings();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"Captured Outgoing Call");
        String dialedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.e(TAG, "dialled NUmber : " + dialedNumber);

        toCountry = CountryUtil.getISOCode(dialedNumber);

        if(dialerSettings.isRedirectionNeeded(toCountry)){
            setResultData(null);
            Intent outgoingCall = new Intent(context, OutgoingcallService.class);
            outgoingCall.setData(Uri.parse("tel:"+dialerSettings.generateDialerNumber(dialedNumber, toCountry)));
            context.startService(outgoingCall);
        }

    }
}
