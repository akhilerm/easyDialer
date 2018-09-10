package com.akhilerm.easydialer;

import java.util.ArrayList;
import java.util.HashMap;

public class CardType {
    private int cardID;
    private String cardName;
    private String dialerNumber;
    private HashMap<String, Integer> languages;
    private int delayAfterDialerNumber;
    private int delayAfterLanguage;
    private int delayAfterPIN;

    public CardType(int cardID, String cardName, String dialerNumber, HashMap<String, Integer> languages, int delayAfterDialerNumber, int delayAfterLanguage, int delayAfterPIN) {
        this.cardID = cardID;
        this.cardName = cardName;
        this.dialerNumber = dialerNumber;
        this.languages = languages;
        this.delayAfterDialerNumber = delayAfterDialerNumber;
        this.delayAfterLanguage = delayAfterLanguage;
        this.delayAfterPIN = delayAfterPIN;
    }

    public int getCardID() {
        return cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public String getDialerNumber() {
        return dialerNumber;
    }

    public HashMap<String, Integer> getLanguages() {
        return languages;
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

    public static CardType getCardType(int cardID, ArrayList<CardType> cardTypes) {
        for (CardType cardType : cardTypes) {
            if (cardType.getCardID() == cardID) {
                return cardType;
            }
        }
        return null;
    }

    public static CardType getCardType(String cardName, ArrayList<CardType> cardTypes) {
        for (CardType cardType : cardTypes) {
            if (cardType.getCardName().equals(cardName)) {
                return cardType;
            }
        }
        return null;
    }
}
