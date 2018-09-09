package com.akhilerm.easydialer;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.telecom.Call;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by akhil on 23/12/17.
 * Captures the phone call states and perform related actions
 */

public class PhoneCallReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneCallReceiver.class.getName() + ":DEBUG:";

    private String toCountry;
    private SettingsData settingsData;
    private DialerSettings dialerSettings;
    static CustomPhoneStateListener customPhoneStateListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        dialerSettings = new DialerSettings(context);
        settingsData = dialerSettings.getSettingsData();
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
        else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (customPhoneStateListener == null) {
                customPhoneStateListener = new CustomPhoneStateListener(context);
                telephonyManager.listen(customPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            }
        }
    }

    public class CustomPhoneStateListener extends PhoneStateListener {
        private final String TAG = CustomPhoneStateListener.class.getName() +" "+ UUID.randomUUID().toString() +" :DEBUG:";
        private int previousState = TelephonyManager.CALL_STATE_IDLE;
        private boolean isIncoming;
        private Context context;

        CustomPhoneStateListener(Context context) {
            this.context = context;
        }

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
            Log.d(TAG, "Dialer Number is " + settingsData.getDialerNumber());
            if(!number.equals(settingsData.getDialerNumber())) return;
            //to delay the query to URI, so that last data is fetched
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Magic here
            ContentValues contentValues = new ContentValues();
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                    CallLog.Calls.TYPE + " = " + CallLog.Calls.OUTGOING_TYPE,
                    null,null);
            cursor.moveToLast();
            DatabaseUtils.cursorRowToContentValues(cursor, contentValues);

            //edit values
            String post_Dial_digits = contentValues.get(CallLog.Calls.POST_DIAL_DIGITS).toString();
            Log.d(TAG, "post digits" + post_Dial_digits);
            int pos = post_Dial_digits.lastIndexOf(',');
            Log.d(TAG, "POS" + pos);
            String number1 = post_Dial_digits.substring(pos+1);
            Log.d(TAG, "Number 1" + number1);
            String originalNumber = "+" + number1.substring(2);
            Log.d(TAG, "Original Number " + originalNumber);
            int duration = Integer.parseInt(contentValues.get(CallLog.Calls.DURATION).toString())-40;
            String ISOCode = CountryUtil.getISOCode(originalNumber);
            contentValues.put(CallLog.Calls.DURATION, duration < 0 ? 0 : duration);
            contentValues.put(CallLog.Calls.POST_DIAL_DIGITS, "");
            contentValues.put(CallLog.Calls.NUMBER, originalNumber);
            contentValues.put(CallLog.Calls.COUNTRY_ISO, ISOCode);
            contentValues.put(CallLog.Calls.GEOCODED_LOCATION, new Locale("",ISOCode).getDisplayCountry());

            //delete
            int rows = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,
                    CallLog.Calls._ID + " = " + contentValues.get(CallLog.Calls._ID) + " " +
                            "AND " + CallLog.Calls.DATE + "=" + contentValues.get(CallLog.Calls.DATE), null);
            Log.d(TAG, "Deleted rows " + rows);
            //insert

            context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, contentValues);
                }
            }, 1000);

        }
    }

}
