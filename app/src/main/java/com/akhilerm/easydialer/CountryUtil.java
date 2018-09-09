package com.akhilerm.easydialer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class CountryUtil {


    static String getISOCode(String phoneNumber) {

        phoneNumber=phoneNumber.replace("+","");
        HashMap <String, String> countryMap = getCountryMap();

        Iterator iterator = countryMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            if (phoneNumber.startsWith(pair.getValue().toString()) ||
                    phoneNumber.startsWith("00" + pair.getValue())) {
                return pair.getKey().toString();
            }
            iterator.remove();
        }

        return "CCD"; //calling card

    }

    static int getISDCode(String ISOCode) {
        String ISDCode = getCountryMap().get(ISOCode).toString();
        if (ISDCode.isEmpty()) {
            return 0;
        }
       return Integer.parseInt(ISDCode);
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
