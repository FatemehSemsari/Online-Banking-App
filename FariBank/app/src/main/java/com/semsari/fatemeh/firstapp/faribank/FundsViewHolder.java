package com.semsari.fatemeh.firstapp.faribank;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class FundsViewHolder extends RecyclerView.ViewHolder{

    TextView fundName;
    LinearLayout fundItem;
    public FundsViewHolder(@NonNull View itemView) {
        super(itemView);
        fundName = itemView.findViewById(R.id.fund_name);
        fundItem = itemView.findViewById(R.id.fund_item);
    }
}
