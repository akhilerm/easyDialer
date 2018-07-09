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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent notificationIntent = new Intent(this, DialReceiver.class);
            PendingIntent pendingIntent= PendingIntent.getActivity(this,0,notificationIntent,0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "101")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("TEST")
                    .setContentText("HELLO")
                    .setTicker("TICKER")
                    .setContentIntent(pendingIntent);
            Notification notification=builder.build();
            NotificationChannel channel = new NotificationChannel("101", "easy Dialer", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("TEst channel");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            startForeground(101, notification);
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "easyDialer deactivated", Toast.LENGTH_SHORT).show();
        unregisterReceiver(dialReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startIdxit) {
        Toast.makeText(this, "easyDialer activated", Toast.LENGTH_SHORT).show();
        dialReceiver = new DialReceiver();
        this.registerReceiver(dialReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));

        return super.onStartCommand(intent, flags, startIdxit);
    }

}
