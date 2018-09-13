package com.akhilerm.easydialer;

import java.util.ArrayList;

public class SettingsData {

    private int cardID;
    private String dialerNumber;
    private int language;
    private String PINNumber;
    private ArrayList<String> countries;
    private int delayAfterDialerNumber;
    private int delayAfterLanguage;
    private int delayAfterPIN;
    private boolean isActive;

    public SettingsData() {
        this(0, "", 0, "", new ArrayList<String>(),
                0,0,0, false);
    }

    public SettingsData(int cardID, String dialerNumber, int language, String PINNumber,
                        ArrayList<String> countries, int delayAfterDialerNumber,
                        int delayAfterLanguage, int delayAfterPIN, boolean isActive) {
        this.cardID = cardID;
        this.dialerNumber = dialerNumber;
        this.language = language;
        this.PINNumber = PINNumber;
        this.countries = countries;
        this.delayAfterDialerNumber = delayAfterDialerNumber;
        this.delayAfterLanguage = delayAfterLanguage;
        this.delayAfterPIN = delayAfterPIN;
        this.isActive = isActive;
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

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setDialerNumber(String dialerNumber) {
        this.dialerNumber = dialerNumber;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setPINNumber(String PINNumber) {
        this.PINNumber = PINNumber;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }
}
