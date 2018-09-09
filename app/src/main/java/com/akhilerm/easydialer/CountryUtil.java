package com.akhilerm.easydialer;

import java.util.HashMap;
import java.util.Locale;

public class CountryUtil {


    static String getISOCode(String phoneNumber) {

        phoneNumber=phoneNumber.replace("+","");
        HashMap <String, String> countryMap = getCountryMap();

        if (phoneNumber.startsWith("91")||phoneNumber.startsWith("0091"))
            return "IN";
        return "CCD"; //calling card

    }

    static int getISDCode(String ISOCode) {
       return getCountryMap().get(ISOCode);
    }

    static String getCountryName(String ISOCode) {
        Locale loc = new Locale("",ISOCode);
        return loc.getDisplayCountry();
    }

    static String[] getCountryArray(String countries) {
        return countries.split(",");
    }

    private static HashMap getCountryMap() {
        HashMap<String, String> countryMap = new HashMap<>();
        countryMap.put("IN","91");
        countryMap.put("PK","92");
        countryMap.put("LK","94");
        countryMap.put("BD","880");
        return countryMap;
    }
}
