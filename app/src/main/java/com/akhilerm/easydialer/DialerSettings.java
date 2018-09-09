package com.akhilerm.easydialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class DialerSettings {

    private static final String TAG = DialerSettings.class.getName() + ":DEBUG:";

    private SettingsData settingsData;
    private SharedPreferences appSettings;

    public DialerSettings(Context context) {
        appSettings= context.getSharedPreferences("Settings", MODE_PRIVATE);
        Gson gson = new Gson();
        settingsData = gson.fromJson(appSettings.getString("SettingsData", ""), SettingsData.class);
    }

    boolean isRedirectionNeeded(String ISOCode) {
        if (settingsData.getCountries().contains(ISOCode)) {
            return true;
        }
        return false;
    }

    String generateDialerNumber(String phoneNumber, String ISOCode) {
        StringBuilder finalNumber = new StringBuilder("");
        finalNumber.append(settingsData.getDialerNumber())
                .append(generateDelay(settingsData.getDelayAfterDialerNumber()))
                .append(settingsData.getLanguage())
                .append(generateDelay(settingsData.getDelayAfterLanguage()))
                .append(settingsData.getPINNumber()).append("#")
                .append(generateDelay(settingsData.getDelayAfterPIN()))
                .append(cleanNumber(phoneNumber, ISOCode));

        return  finalNumber.toString();
    }

    private String generateDelay(int n) {
        StringBuilder delay = new StringBuilder("");
        for (int i = 0; i < n; i ++) {
            delay.append(",");
        }
        return delay.toString();
    }

    /**
     * Cleans the phone number using the country ISO Code. ie, converts +91 9876543210 to 00919876543210
     * @param phoneNumber
     * @param ISOCode
     * @return
     */
    private String cleanNumber(String phoneNumber, String ISOCode) {
        phoneNumber=phoneNumber.replace("+","");
        phoneNumber=phoneNumber.replaceFirst(CountryUtil.getISDCode(ISOCode)+"","");
        phoneNumber = "00" + CountryUtil.getISDCode(ISOCode) + phoneNumber;
        return phoneNumber;
    }

    boolean saveSettings(SettingsData settingsData) {
        this.settingsData = settingsData;
        return saveSettings();
    }

    private boolean saveSettings(){
        SharedPreferences.Editor editor = appSettings.edit();
        Gson gson = new Gson();
        editor.putString("SettingsData", gson.toJson(settingsData));
        editor.apply();
        return true;
    }

    /**
     * Check if valid settings are present
     * @return
     */
    boolean isValid() {
        return settingsData.isValid();
    }

    public boolean isServiceActive() {
        return settingsData.isActive();
    }

    public boolean toggleServiceStatus(boolean status) {
        SharedPreferences.Editor editor = appSettings.edit();
        settingsData.setActive(status);
        saveSettings();
        return status;
    }
}

