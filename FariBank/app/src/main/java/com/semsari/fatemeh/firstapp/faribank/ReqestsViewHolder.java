package com.semsari.fatemeh.firstapp.faribank;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReqestsViewHolder extends RecyclerView.ViewHolder {

    private TextView sender;
    private TextView request;
    public ReqestsViewHolder(@NonNull View itemView) {
        super(itemView);
        sender = itemView.findViewById(R.id.sender);
        request = itemView.findViewById(R.id.request_message);
    }

    public void bind(UserRequests userRequests, String sender){
        this.sender.setText(sender);
        request.setText(userRequests.getText());

        if(userRequests.isUserRequest()){
            this.sender.setGravity(Gravity.END);
            request.setGravity(Gravity.END);
            request.setBackgroundResource(R.drawable.user_message_background);
        } else {
            this.sender.setGravity(Gravity.START);
            request.setGravity(Gravity.START);
            request.setBackgroundResource(R.drawable.manager_message_background);
        }

    }
}
