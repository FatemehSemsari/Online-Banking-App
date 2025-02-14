package com.semsari.fatemeh.firstapp.faribank;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView contactName;
    ImageView deleteBtn;
    LinearLayout llRow;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        contactName = itemView.findViewById(R.id.contact_name);
        llRow = itemView.findViewById(R.id.llRow);
        deleteBtn = itemView.findViewById(R.id.delete);
    }
}
