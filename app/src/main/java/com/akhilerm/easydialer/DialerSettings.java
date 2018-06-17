package com.akhilerm.easydialer;

public class DialerSettings {

    private int dialerNumber;
    private int PINNumber;
    private int dialerLanguage;

    public DialerSettings() {

    }

    boolean isRedirectionNeeded(int ISOCode) {
        return true;
    }

    String generateDialerNumber(String phoneNumber, int ISOCode) {

        return dialerNumber + "," + dialerLanguage + PINNumber + "#,," + cleanNumber(phoneNumber, ISOCode);
    }

    /**
     * Cleans the phone number using the country ISO Code. ie, converts +91 9876543210 to 00919876543210
     * @param phoneNumber
     * @param ISOCode
     * @return
     */
    private String cleanNumber(String phoneNumber, int ISOCode) {
        int ISDCode = CountryUtil.getISDCode(ISOCode);
        return "";
    }
}
