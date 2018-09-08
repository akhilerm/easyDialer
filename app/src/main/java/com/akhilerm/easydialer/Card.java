package com.akhilerm.easydialer;

import java.util.ArrayList;

public class Card {

    private CardType cardType;
    private String PINNumber;
    private ArrayList<String> countries;

    public Card(CardType cardType, String PINNumber, ArrayList<String> countries) {
        this.cardType = cardType;
        this.PINNumber = PINNumber;
        this.countries = countries;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getPINNumber() {
        return PINNumber;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }
}
