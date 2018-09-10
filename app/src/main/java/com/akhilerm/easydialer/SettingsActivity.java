package com.akhilerm.easydialer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private DialerSettings dialerSettings;
    private SettingsData settingsData;
    private Spinner cardTypeSpinner;
    private ArrayAdapter<String> cardTypeAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dialerSettings = new DialerSettings(getApplicationContext());
        settingsData = dialerSettings.getSettingsData();

        cardTypeSpinner = findViewById(R.id.cardType);
    }

    private ArrayList<CardType> loadCards() {
        ArrayList<CardType> cardTypes = new ArrayList<>();

        String json = loadJSONFromAsset();

        Gson gson = new Gson();

        cardTypes = gson.fromJson(json, new TypeToken<List<CardType>>(){}.getType());

        return cardTypes;
    }

    private String loadJSONFromAsset() {
        String json = null;

        try {
            InputStream inputStream = getAssets().open("CardDetails.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return json;
    }
}
