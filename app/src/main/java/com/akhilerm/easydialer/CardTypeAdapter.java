package com.akhilerm.easydialer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardTypeAdapter extends ArrayAdapter {
    private List<CardType> cardTypeList = new ArrayList<>();

    CardTypeAdapter(@NonNull Context context, int resource, int spinnerText, @NonNull List<CardType> cardTypeList) {
        super(context, resource, spinnerText, cardTypeList);
        this.cardTypeList = cardTypeList;
    }

    @Override
    public CardType getItem(int position) {
        return cardTypeList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    private View initView(int position) {
        CardType state = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.cardtype_list, null);
        TextView textView =  v.findViewById(R.id.cardSpinnerText);
        textView.setText(state.getCardName());
        return v;
    }
}
