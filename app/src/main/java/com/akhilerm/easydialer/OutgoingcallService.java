package com.akhilerm.easydialer;
import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.telecom.InCallService;
import android.util.Log;

/**
 * Created by akhil on 24/1/18.
 * Creates the actual outgoing call from the app
 */

public class OutgoingcallService extends IntentService {
    private static final String TAG = OutgoingcallService.class.getName();
    private InCallService inCallService;

    public OutgoingcallService (String name) {
        super(name);
    }

    public OutgoingcallService() {
        super(TAG);
    }

    @TargetApi(23)
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"on handle inten");
        Intent outgoingcall = new Intent(Intent.ACTION_CALL);
        outgoingcall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        outgoingcall.setData(intent.getData());
        startActivity(outgoingcall);
        inCallService = new InCallService() {
            @Override
            public IBinder onBind(Intent intent) {
                return super.onBind(intent);
            }
        };

    }
}
