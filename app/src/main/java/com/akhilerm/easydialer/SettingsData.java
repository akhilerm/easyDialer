package com.akhilerm.easydialer;

import java.util.ArrayList;

public class SettingsData {

    private String dialerNumber;
    private int language;
    private String PINNumber;
    private ArrayList<String> countries;
    private int delayAfterDialerNumber;
    private int delayAfterLanguage;
    private int delayAfterPIN;
    private boolean isActive = false;

    public SettingsData(CardType cardType, String PINNumber, ArrayList<String> countries) {
        this.PINNumber = PINNumber;
        this.countries = countries;
    }

    public String getPINNumber() {
        return PINNumber;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public String getDialerNumber() {
        return dialerNumber;
    }

    public int getLanguage() {
        return language;
    }

    public int getDelayAfterDialerNumber() {
        return delayAfterDialerNumber;
    }

    public int getDelayAfterLanguage() {
        return delayAfterLanguage;
    }

    public int getDelayAfterPIN() {
        return delayAfterPIN;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isValid() {
        return !dialerNumber.isEmpty() &&
                (language != 0) &&
                !PINNumber.isEmpty() &&
                !countries.isEmpty();
    }
}
