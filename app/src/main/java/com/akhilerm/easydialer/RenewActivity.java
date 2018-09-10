package com.akhilerm.easydialer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RenewActivity extends AppCompatActivity {

    private EditText pinNumber;
    private Button saveButton;
    private DialerSettings dialerSettings;
    private SettingsData settingsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew);

        dialerSettings = new DialerSettings(getApplicationContext());
        settingsData = dialerSettings.getSettingsData();

        pinNumber = findViewById(R.id.pinNumberEditor);
        saveButton = findViewById(R.id.savePINButton);

        pinNumber.setText(settingsData.getPINNumber());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsData.setPINNumber(pinNumber.getText().toString());
                dialerSettings.saveSettings(settingsData);
                Toast.makeText(RenewActivity.this, "New PIN saved", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
