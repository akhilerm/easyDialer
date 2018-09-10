package com.akhilerm.easydialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + ":DEBUG:";
    private final int MULTIPLE_PERMISSIONS_REQUEST = 50;
    private boolean isPermissionAvailable = false;

    private FloatingActionButton renewButton, settingsButton;
    private Button toggleButton;
    private TextView pinNumber;
    private DialerSettings dialerSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        pinNumber = findViewById(R.id.pinNumberView);
        toggleButton = findViewById(R.id.toggleButton);
        settingsButton = findViewById(R.id.settingsButton);
        renewButton = findViewById(R.id.renewButton);

        dialerSettings = new DialerSettings(getApplicationContext());

        setSupportActionBar(toolbar);

        checkPermissionAvailable();
        setFields();

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialerSettings.isServiceActive()) {
                    dialerSettings.toggleServiceStatus(false);
                    stopCallService();
                    toggleButton.setText("Start");
                } else {
                    dialerSettings.toggleServiceStatus(true);
                    startCallService();
                    toggleButton.setText("Stop");
                }
            }
        });

        renewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RenewActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    isPermissionAvailable = true;

                } else {
                    isPermissionAvailable = false;
                    Toast.makeText(this, "Oops! Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void checkPermissionAvailable() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE,
                            Manifest.permission.PROCESS_OUTGOING_CALLS,Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG},
                    MULTIPLE_PERMISSIONS_REQUEST);
        } else {
            isPermissionAvailable = true;
        }
    }

    private void startCallService(){
        String message = "";
        if (!dialerSettings.isValid()) {
            message = "Invalid settings detected";
        } else if (!isPermissionAvailable) {
            message = "Oops! No permission to activate";
        } else {
            getApplicationContext().startService(new Intent(getApplicationContext(), CallService.class));
            message = "easyDialer activated";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void stopCallService() {
        Toast.makeText(this, "easyDialer deactivated", Toast.LENGTH_SHORT).show();
        getApplicationContext().stopService(new Intent(getApplicationContext(), CallService.class));
    }

    /**
     * Loads the saved values to the fields if exists. Else a blank or default option is shown.
     * @return
     */
    private boolean setFields() {
        if (dialerSettings.isServiceActive()) {
            toggleButton.setText("Stop");
        }
        else {
            toggleButton.setText("Start");
        }
        pinNumber.setText(dialerSettings.getSettingsData().getPINNumber());
        return true;
    }

}
