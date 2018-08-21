package com.akhilerm.easydialer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akhil on 15/1/18.
 */

public class CallService extends Service {

    private static final String TAG = CallService.class.getName() + ":DEBUG:";
    private static PhoneCallReceiver phoneCallReceiver;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent= PendingIntent.getActivity(this,0,notificationIntent,0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "101")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("easyDialer is Active")
                    .setContentText("Calls to "+ TextUtils.join(",",new DialerSettings(getApplicationContext()).getCountries())+" are being rerouted")
                    .setContentIntent(pendingIntent);
            Notification notification=builder.build();
            NotificationChannel channel = new NotificationChannel("101", "easy Dialer", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("TEst channel");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            startForeground(1001, notification);
        }
        Toast.makeText(this, "easyDialer activated", Toast.LENGTH_SHORT).show();
        phoneCallReceiver = new PhoneCallReceiver();
        this.registerReceiver(phoneCallReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startIdxit) {
        Log.d(TAG, "Service started");
        phoneCallReceiver = new PhoneCallReceiver();
        this.registerReceiver(phoneCallReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
        return super.onStartCommand(intent, flags, startIdxit);
    }

    @Override
    public boolean stopService(Intent intent) {
        Log.d(TAG, "Service stopped");
        unregisterReceiver(phoneCallReceiver);
        return true;
    }

}
