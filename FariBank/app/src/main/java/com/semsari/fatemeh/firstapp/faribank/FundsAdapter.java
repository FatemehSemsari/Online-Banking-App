package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FundsAdapter extends RecyclerView.Adapter<FundsViewHolder> {

    private List<Fund> userFunds;
    User user = null;
    public FundsAdapter(List<Fund> userFunds, User user) {
        this.userFunds = userFunds;
        this.user = user;
    }

    @NonNull
    @Override
    public FundsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.funds_list_item, parent, false);
        FundsViewHolder fundsViewHolder = new FundsViewHolder(view);
        return fundsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FundsViewHolder holder, int position) {
        holder.fundName.setText(userFunds.get(position).getFundType().toString());
        holder.fundItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ManageFundActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                intent.putExtra("position", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userFunds.size();
    }
}
