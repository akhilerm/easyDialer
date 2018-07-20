package com.akhilerm.easydialer;

import java.util.HashMap;

public class CountryUtil {

    static String getISOCode(String phoneNumber) {
        phoneNumber=phoneNumber.replace("+","");
        if (phoneNumber.startsWith("91")||phoneNumber.startsWith("0091"))
            return "IN";
        return "CCD"; //calling card
    }

    static int getISDCode(String ISOCode) {
       if(ISOCode.equals("IN")) {
           return 91;
       }
       return 0;
    }

    static String getCountryName(String ISOCode) {
        if (ISOCode.equals("IN")) {
            return "India";
        }
        return "NOt found";
    }

    static String[] getCountryArray(String countries) {
        return countries.split(",");
    }
}
