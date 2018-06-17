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
        Log.d(TAG,"Captured Outgoing Call");
        String dialedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.d(TAG, "Dialled Number : " + dialedNumber);
        toCountry = CountryUtil.getISOCode(dialedNumber);
        Log.d(TAG, "To Country : " + CountryUtil.getCountryName(toCountry));
        if(dialerSettings.isRedirectionNeeded(toCountry)){
            Log.d(TAG, "Call redirection");
            setResultData(null);
            Intent outgoingCall = new Intent(context, OutgoingcallService.class);
            String formattedNumber = dialerSettings.generateDialerNumber(dialedNumber, toCountry);
            Log.d(TAG, "Formatted Number : " + formattedNumber);
            outgoingCall.setData(Uri.parse("tel:"+ formattedNumber));
            context.startService(outgoingCall);
        }

    }
}
