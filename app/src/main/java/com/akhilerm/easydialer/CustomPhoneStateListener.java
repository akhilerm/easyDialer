package com.akhilerm.easydialer;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomPhoneStateListener extends PhoneStateListener {
    private static final String TAG = CustomPhoneStateListener.class.getName() + ":DEBUG:";
    private int previousState = TelephonyManager.CALL_STATE_IDLE;
    private Context context;

    CustomPhoneStateListener (Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            Log.d(TAG, "Phone nummber : " + phoneNumber);
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                if (previousState == TelephonyManager.CALL_STATE_IDLE) {
                    Log.d(TAG, "Incoming call " + phoneNumber);
                    previousState = state;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (previousState == TelephonyManager.CALL_STATE_IDLE) {
                    Log.d(TAG, "outgoing call " + phoneNumber);
                    previousState = state;
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
                    Log.d(TAG, "incomig /outgoin Call ended " + phoneNumber);
                    previousState = state;
                }
                break;
        }
    }
}
