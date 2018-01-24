package com.akhilerm.easydialer;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by akhil on 24/1/18.
 */

public class OutgoingcallService extends IntentService {
    private static final String TAG = OutgoingcallService.class.getName();

    public OutgoingcallService (String name) {
        super(name);
    }

    public OutgoingcallService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"on handle inten");
        Intent outgoingcall = new Intent(Intent.ACTION_CALL);
        outgoingcall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        outgoingcall.setData(intent.getData());
        startActivity(outgoingcall);
    }
}
