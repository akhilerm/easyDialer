package com.akhilerm.easydialer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private DialerSettings dialerSettings;
    private SettingsData settingsData;
    private Button saveButton;
    private Spinner cardTypeSpinner;
    private Spinner languageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cardTypeSpinner = findViewById(R.id.cardTypeSpinner);
        languageSpinner = findViewById(R.id.languageSpinner);
        saveButton = findViewById(R.id.saveSettingsButton);

        dialerSettings = new DialerSettings(getApplicationContext());
        settingsData = dialerSettings.getSettingsData();

        loadCardTypeDetails();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardType cardType = (CardType) cardTypeSpinner.getSelectedItem();
                settingsData.setDialerNumber(cardType.getDialerNumber());
                settingsData.setLanguage(cardType.getLanguages().get(languageSpinner.getSelectedItem()));

                if (settingsData.isValid()) {
                    dialerSettings.saveSettings(settingsData);
                    Toast.makeText(SettingsActivity.this, "Settings Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SettingsActivity.this, "Invalid Configuration", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private void loadCardTypeDetails() {
        final List<CardType> cardTypeList = loadCards();
        List<String> cardTypes = new ArrayList<>();

        for (CardType cardType : cardTypeList) {
            cardTypes.add(cardType.getCardName());
        }

        final CardTypeAdapter cardTypeAdapter = new CardTypeAdapter(SettingsActivity.this, R.layout.cardtype_list,
                R.id.cardSpinnerText, cardTypeList);
        cardTypeSpinner.setAdapter(cardTypeAdapter);

        cardTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> languageList = new ArrayList<>();
                for (String language :cardTypeAdapter.getItem(position).getLanguages().keySet()) {
                    languageList.add(language);
                }

                ArrayAdapter languagesAdapter  = new ArrayAdapter(SettingsActivity.this, R.layout.language_list,
                        R.id.languageSpinnerText, languageList);
                languageSpinner.setAdapter(languagesAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
