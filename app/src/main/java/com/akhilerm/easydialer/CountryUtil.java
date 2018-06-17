package com.akhilerm.easydialer;

import java.util.HashMap;

public class CountryUtil {

    private

    static String getISOCode(String phoneNumber) {
        phoneNumber.replace("+","");
        if (phoneNumber.startsWith("91"))
            return "IN";
        return "IN";
    }

    static int getISDCode(String ISOCode) {
       if(ISOCode.equals("IN")) {
           return 91;
       }
       return 91;
    }

    static String getCountryName(String ISOCode) {
        if (ISOCode.equals("IN")) {
            return "India";
        }
        return "India";
    }
}
