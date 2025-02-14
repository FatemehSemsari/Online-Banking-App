package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {


    LayoutInflater layoutInflater;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.spinner_item, null, true);
        String desCardNo = getItem(position);
        TextView desCardNumber = rowView.findViewById(R.id.recent_user_card_number);
        desCardNumber.setText(desCardNo);
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.spinner_item, parent, false);
        }
        String desCardNo = getItem(position);
        TextView desCardNumber = convertView.findViewById(R.id.recent_user_card_number);
        desCardNumber.setText(desCardNo);
        return convertView;
    }
}
