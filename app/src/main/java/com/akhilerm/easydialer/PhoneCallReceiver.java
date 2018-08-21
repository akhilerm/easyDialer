package com.akhilerm.easydialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by akhil on 23/12/17.
 * Captures the phone call states and perform related actions
 */

public class PhoneCallReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneCallReceiver.class.getName() + ":DEBUG:";

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
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CustomPhoneStateListener customPhoneStateListener = new CustomPhoneStateListener();
        telephonyManager.listen(customPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public class CustomPhoneStateListener extends PhoneStateListener {
        private final String TAG = CustomPhoneStateListener.class.getName() + ":DEBUG:";
        private int previousState = TelephonyManager.CALL_STATE_IDLE;
        private boolean isIncoming;

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Log.d(TAG, "Phone nummber : " + phoneNumber);
            }
            if (previousState == state) {
                return;
            }
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    if (previousState == TelephonyManager.CALL_STATE_IDLE) {
                        Log.d(TAG, "Incoming call " + phoneNumber);
                        previousState = state;
                        isIncoming = true;
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (previousState == TelephonyManager.CALL_STATE_IDLE) {
                        Log.d(TAG, "outgoing call " + phoneNumber);
                        previousState = state;
                        isIncoming = false;
                    }
                    else if (previousState == TelephonyManager.CALL_STATE_RINGING) {
                        Log.d(TAG, "Took incoming call " + phoneNumber);
                        previousState = state;
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (previousState == TelephonyManager.CALL_STATE_RINGING) {
                        Log.d(TAG, "Missed call " + phoneNumber);
                        previousState = state;
                    }
                    else if (previousState == TelephonyManager.CALL_STATE_OFFHOOK) {
                        if (isIncoming) {
                            Log.d(TAG, "Incoming call ended " + phoneNumber);
                        }
                        else {
                            Log.d(TAG, "Outgoing call ended " + phoneNumber);
                            onOutgoingCallEnded(phoneNumber);
                        }
                        previousState = state;
                    }
                    break;
            }
        }


        private void onOutgoingCallEnded(String number) {
            // Code to edit call log
        }
    }

}
