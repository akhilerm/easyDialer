package com.akhilerm.easydialer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class DialerSettings {

    private static final String DIALER_NUMBER = "DialerNumber";
    private static final String PIN_NUMBER = "PINNumber";
    private static final String DIALER_LANGUAGE = "DialerLanguage";
    private static final String COUNTRIES = "Country";

    private String dialerNumber;
    private String PINNumber;
    private int dialerLanguage;
    private String[] countries;
    private SharedPreferences settingsData;

    public DialerSettings(Context context) {
        settingsData= context.getSharedPreferences("Settings", MODE_PRIVATE);
        dialerNumber = settingsData.getString(DIALER_NUMBER, "800505");
        PINNumber = settingsData.getString(PIN_NUMBER, "");
        dialerLanguage = settingsData.getInt(DIALER_LANGUAGE, 9);
        countries = settingsData.getString(COUNTRIES,"IN").split(",");
    }

    boolean isRedirectionNeeded(String ISOCode) {
        if (Arrays.asList(countries).contains(ISOCode)) {
            return true;
        }
        return false;
    }

    String generateDialerNumber(String phoneNumber, String ISOCode) {

        return dialerNumber + "," + dialerLanguage + PINNumber + "#,," + cleanNumber(phoneNumber, ISOCode);
    }

    /**
     * Cleans the phone number using the country ISO Code. ie, converts +91 9876543210 to 00919876543210
     * @param phoneNumber
     * @param ISOCode
     * @return
     */
    private String cleanNumber(String phoneNumber, String ISOCode) {
        int ISDCode = CountryUtil.getISDCode(ISOCode);
        phoneNumber=phoneNumber.replace("+","");
        phoneNumber=phoneNumber.replace(CountryUtil.getISDCode(ISOCode)+"","");
        phoneNumber = "00" + CountryUtil.getISDCode(ISOCode) + phoneNumber;
        return phoneNumber;
    }

    boolean saveSettings(String dialerNumber, String PINNumber, int dialerLanguage, String[] countries) {
        SharedPreferences.Editor editor = settingsData.edit();

        this.dialerNumber = dialerNumber;
        this.PINNumber = PINNumber;
        this.dialerLanguage = dialerLanguage;
        this.countries = countries;

        StringBuilder countryArray = new StringBuilder("");
        for (String country : countries) {
            countryArray.append(country + ",");
        }

        editor.putString(DIALER_NUMBER, dialerNumber);
        editor.putString(PIN_NUMBER, PINNumber);
        editor.putInt(DIALER_LANGUAGE, dialerLanguage);
        editor.putString(COUNTRIES, countryArray.toString());
        return true;
    }

    /**
     * Check if valid settings are present
     * @return
     */
    boolean isValid() {
        return true;
    }
}
