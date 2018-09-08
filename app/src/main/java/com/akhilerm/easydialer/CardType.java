package com.akhilerm.easydialer;

public class CardType {
    int cardID;
    String cardName;
    String dialerNumber;
    String[] languages;

    public CardType(int cardID, String cardName, String dialerNumber, String[] languages) {
        this.cardID = cardID;
        this.cardName = cardName;
        this.dialerNumber = dialerNumber;
        this.languages = languages;
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

    public String[] getLanguages() {
        return languages;
    }
}
