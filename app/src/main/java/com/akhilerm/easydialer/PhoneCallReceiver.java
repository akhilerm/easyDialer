package com.akhilerm.easydialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by akhil on 23/12/17.
 * Captures the phone call states and perform related actions
 */

public class PhoneCallReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneCallReceiver.class.getName();

    private String toCountry;
    private DialerSettings dialerSettings;

    @Override
    public void onReceive(Context context, Intent intent) {
        dialerSettings = new DialerSettings(context);
        Log.d(TAG,"Captured Outgoing Call");
        String dialedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.d(TAG, "Dialled Number : " + dialedNumber);
        toCountry = CountryUtil.getISOCode(dialedNumber);
        Log.d(TAG, "To Country : " + CountryUtil.getCountryName(toCountry));
        Log.d(TAG, "is Direction : " + dialerSettings.isRedirectionNeeded(toCountry));
        if(dialerSettings.isRedirectionNeeded(toCountry)){
            Log.d(TAG, "Call redirection");
            setResultData(null);
            Intent outgoingCall = new Intent(context, OutgoingcallService.class);
            String formattedNumber = dialerSettings.generateDialerNumber(dialedNumber, toCountry);
            Log.d(TAG, "Formatted Number : " + formattedNumber);
            outgoingCall.setData(Uri.parse("tel:"+ Uri.encode(formattedNumber)));
            context.startService(outgoingCall);
        }

    }
}
